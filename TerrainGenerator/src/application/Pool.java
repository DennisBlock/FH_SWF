package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pool<E> {

	private PoolObjectFactory<E> objectFactory;
	private int maxSize;
	private List<E> freeObjects;

	public Pool(PoolObjectFactory<E> objectFactory, int maxSize) {
		this.objectFactory = objectFactory;
		this.maxSize = maxSize;
		freeObjects = new ArrayList<E>(maxSize);
	}

	public E get() {
		E object = null;

		if (freeObjects.size() == 0)
			object = objectFactory.create();
		else
			object = freeObjects.remove(freeObjects.size() - 1);

		return object;
	}
	
	public void free(E object) {
		if(freeObjects.size() < maxSize)
			freeObjects.add(object);
	}
	
	public void free(List<E> objects) {
		if(maxSize - freeObjects.size() > objects.size())
			freeObjects.addAll(objects);
		else {
			freeObjects.addAll(objects.subList(0, maxSize - freeObjects.size() - 1));
		}
	}

}
