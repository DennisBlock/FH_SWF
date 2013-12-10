package aufgabe1.util;

import aufgabe1.Person;

public class Pair implements Comparable<Pair>
{

    private final Double left;
    private final Person right;

    public Pair(Double left, Person right)
    {
	this.left = left;
	this.right = right;
    }

    public Double getLeft()
    {
	return left;
    }

    public Person getRight()
    {
	return right;
    }

    @Override
    public boolean equals(Object o)
    {
	if (o == null)
	    return false;
	if (!(o instanceof Pair))
	    return false;
	Pair pair = (Pair) o;

	return this.left.equals(pair.getLeft())
		&& this.right.equals(pair.getRight());
    }

    @Override
    public int compareTo(Pair o)
    {
	return left.compareTo(o.getLeft());
    }

    @Override
    public String toString()
    {
	return "Pair [left=" + left + ", right=" + right + "]";
    }
}
