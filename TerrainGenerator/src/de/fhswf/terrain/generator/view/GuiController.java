package de.fhswf.terrain.generator.view;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import de.fhswf.terrain.generator.Terrain;

/**
 * The controller for the gui. This class provides the basic application layout
 * containing a menu bar and space where other JavaFX elements can be placed.
 */
public class GuiController {
	@FXML
	private Button generateButton;
	@FXML
	private Slider deepWaterSlider;
	@FXML
	private Slider waterSlider;
	@FXML
	private Slider sandSlider;
	@FXML
	private Slider grassSlider;
	@FXML
	private Slider hillsSlider;
	@FXML
	private Slider mountainSlider;
	@FXML
	private Slider everestSlider;
	@FXML
	private Label deepWaterLabel;
	@FXML
	private Label waterLabel;
	@FXML
	private Label sandLabel;
	@FXML
	private Label grassLabel;
	@FXML
	private Label hillsLabel;
	@FXML
	private Label mountainLabel;
	@FXML
	private Label everestLabel;
	@FXML
	private ComboBox<String> terrainSizeComboBox;
	@FXML
	private TextField seedTextField;
	@FXML
	private TextField hTextField;
	@FXML
	private ImageView mapImageView;
	@FXML
	private CheckBox seedCheckBox;

	private TerrainGenerator terrainGenerator;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		terrainGenerator = new TerrainGenerator();
		final DecimalFormat f = new DecimalFormat("#0.00");
		// Map size data as an observable list of Strings
		ObservableList<String> options = FXCollections.observableArrayList(
				"513x513", "1025x1025");

		terrainSizeComboBox.setItems(options);
		// Set 1025x1025 as default value
		terrainSizeComboBox.setValue("1025x1025");

		// Set the labels with the pretended biom values
		deepWaterLabel.setText(f.format(deepWaterSlider.getValue()) + "");
		waterLabel.setText(f.format(waterSlider.getValue()) + "");
		sandLabel.setText(f.format(sandSlider.getValue()) + "");
		grassLabel.setText(f.format(grassSlider.getValue()) + "");
		hillsLabel.setText(f.format(hillsSlider.getValue()) + "");
		mountainLabel.setText(f.format(mountainSlider.getValue()) + "");
		everestLabel.setText(f.format(everestSlider.getValue()) + "");

		// Add listener to the sliders to change the labels.
		addListenerToSliders(f);

		// Generate a map
		generateAction();
	}

	/**
	 * Add listener to the sliders to change the labels.
	 * 
	 * @param f
	 *            Decimal format for the output eg. 0.00
	 */
	private void addListenerToSliders(final DecimalFormat f) {
		deepWaterSlider.valueProperty().addListener(
				new ChangeListener<Number>() {
					@Override
					public void changed(
							ObservableValue<? extends Number> observableValue,
							Number oldValue, Number newValue) {
						if (newValue == null) {
							deepWaterLabel.setText("");
							return;
						}
						deepWaterLabel.setText(f.format(newValue.doubleValue())
								+ "");
					}
				});

		waterSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue, Number newValue) {
				if (newValue == null) {
					waterLabel.setText("");
					return;
				}
				waterLabel.setText(f.format(newValue.doubleValue()) + "");
			}
		});

		sandSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue, Number newValue) {
				if (newValue == null) {
					sandLabel.setText("");
					return;
				}
				sandLabel.setText(f.format(newValue.doubleValue()) + "");
			}
		});

		grassSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue, Number newValue) {
				if (newValue == null) {
					grassLabel.setText("");
					return;
				}
				grassLabel.setText(f.format(newValue.doubleValue()) + "");
			}
		});

		hillsSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue, Number newValue) {
				if (newValue == null) {
					hillsLabel.setText("");
					return;
				}
				hillsLabel.setText(f.format(newValue.doubleValue()) + "");
			}
		});

		mountainSlider.valueProperty().addListener(
				new ChangeListener<Number>() {
					@Override
					public void changed(
							ObservableValue<? extends Number> observableValue,
							Number oldValue, Number newValue) {
						if (newValue == null) {
							mountainLabel.setText("");
							return;
						}
						mountainLabel.setText(f.format(newValue.doubleValue())
								+ "");
					}
				});

		everestSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue, Number newValue) {
				if (newValue == null) {
					everestLabel.setText("");
					return;
				}
				everestLabel.setText(f.format(newValue.doubleValue()) + "");
			}
		});
	}

	/**
	 * Called when the user clicks on Generate Button. Checks whether the
	 * entries are correct and starts a thread which executes the DiamondSquare
	 * algorithm.
	 */
	@FXML
	private void generateAction() {
		// Check input values
		if (isInputValid()) {
			// Run the DiamondSquare algorithm
			new RenderThread().start(); 
		}
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (seedTextField.getText() == null
				|| seedTextField.getText().length() == 0) {
			errorMessage += "No valid seed Value!\n";
		}
		if (hTextField.getText() == null || hTextField.getText().length() == 0) {
			errorMessage += "No valid h Value !\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Dialogs.showErrorDialog(dialogStage, errorMessage,
					"Please correct invalid fields", "Invalid Fields");
			return false;
		}
	}

	/**
	 * Opens a FileChooser to let the user select a file to save to.
	 */
	@FXML
	private void handleSave() {

		// Setup the FileChooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Image");
		fileChooser.setInitialFileName("Terrain.png");
		
		// Show save file dialog
		File file = fileChooser.showSaveDialog(terrainGenerator
				.getPrimaryStage());

		if (file != null) {
			try {
				// Writes an image
				ImageIO.write(
						SwingFXUtils.fromFXImage(mapImageView.getImage(), null),
						"png", file);
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
				Dialogs.showErrorDialog(terrainGenerator.getPrimaryStage(),
						"Could not save image to file:\n" + file.getPath(),
						"Could not save data", "Error", ex);
			}
		}
	}

	/**
	 * Opens an about dialog.
	 */
	@FXML
	private void handleAbout() {
		
		// Show save about dialog
		Dialogs.showInformationDialog(
				terrainGenerator.getPrimaryStage(),
				"Autoren: Dennis Block, Greogr Block\nAusarbeitung: Spezielle Algorithmen",
				"TerrainGenerator", "About");
	}

	/**
	 * Called when the user clicks on RandomSeed CheckBox
	 */
	@FXML
	private void handleRandomSeed() {
		
		// Disable the seed TextField corresponding to the seed CheckBox
		if (seedCheckBox.isSelected()) {
			seedTextField.setDisable(true);
		} else {
			seedTextField.setDisable(false);
		}
	}

	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}

	/**
	 * Die Berechnung wird in einem eigenen Thread ausgeführt damit die
	 * Oberfläche nicht "einfriert" und auch während der Berechnung weiter
	 * bedienbar bleibt.
	 */
	private class RenderThread extends Thread {

		private double deepWater;
		private double water;
		private double sand;
		private double grass;
		private double hills;
		private double mountain;
		private double everest;
		private int size;
		long seed;

		/**
		 * Initializes the RenderThread class. This method is automatically
		 * called before the thread starts.
		 */
		public RenderThread() {
			// Get the biom values 
			deepWater = deepWaterSlider.getValue();
			water = waterSlider.getValue();
			sand = sandSlider.getValue();
			grass = grassSlider.getValue();
			hills = hillsSlider.getValue();
			mountain = mountainSlider.getValue();
			everest = everestSlider.getValue();

			// Set the size of the map/terrain
			if (terrainSizeComboBox.getValue() == "513x513") {
				size = 513;
			} else {
				size = 1025;
			}

			// Check whether random seed is selected 
			if (seedCheckBox.isSelected()) {
				Random rand = new Random();
				seed = rand.nextLong();
				seedTextField.setText(seed + "");
			} else {
				seed = Double.valueOf(seedTextField.getText()).longValue();
			}
		}

		@Override
		public void run() {
			// Create a Terrain object with the given biom values
			Terrain terrain = new Terrain(size, Double.valueOf(hTextField
					.getText()), Double.valueOf(seedTextField.getText())
					.longValue());

			// Set the created map to the ImageView
			mapImageView.setImage(terrain.createTerrain(deepWater, water, sand,
					grass, hills, mountain, everest));
		}
	}
}