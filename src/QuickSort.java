import java.util.*;

/**
  * Implementation of the Quick Sort algorithm on a generic type of ArrayList.
  * @author Zachary Ferguson
  * @version 1.0
  */
public class QuickSort
{
	/**
	  * Sorts the given ArrayList of type T where T is comparable. Leaves the
	  * original ArrayList unchanged and returns a new sorted shallow copy.
	  * @param <T> Generic typing variable. T must implement the comparable
	  * 	interface.
	  * @param al ArrayList of type T to be sorted.
	  * @return Returns a new Shallow copy of the given ArrayList that is
	  * sorted as specified by T's compareTo method.
	  */
	public static <T extends Comparable<? super T>> ArrayList<T>
		sort(ArrayList<T> al)
	{
		if(al.size() <= 1)
		{
			return al;
		}
		T pivot = al.get(al.size()-1);
		ArrayList<T> smaller = new ArrayList<T>(),
					 larger = new ArrayList<T>(),
					 same = new ArrayList<T>();
		for(T item : al)
		{
			if(item.compareTo(pivot) < 0)
			{
				smaller.add(item);
			}
			else if(item.compareTo(pivot) == 0)
			{
				same.add(item);
			}
			else if(item.compareTo(pivot) > 0)
			{
				larger.add(item);
			}
		}
		return combine(sort(smaller), same, sort(larger));
	}

	/**
	* Combines the three given ArrayLists of type T in to one ArrayList of type
	* T. The order of the ArrayList combination is in the same order as they
	* were given.
	* @param <T> Generic typing variable. T must implement the comparable
	*	interface.
	* @param al1 The first ArrayList of type T to be added to the new ArrayList
	*	of type T.
	* @param al2 The second ArrayList of type T to be added to the new ArrayList
	*	of type T.
	* @param al3 The third ArrayList of type T to be added to the new ArrayList
	*	of type T.
	* @return Returns a new ArrayList of type T that contains the elements of
	*	the given ArrayLists.
	*/
	private static <T extends Comparable<? super T>> ArrayList<T>
		combine(ArrayList<T> al1, ArrayList<T> al2, ArrayList<T> al3)
	{
		ArrayList<T> combined = new ArrayList<T>();
		for(T item : al1)
		{
			combined.add(item);
		}
		for(T item : al2)
		{
			combined.add(item);
		}
		for(T item : al3)
		{
			combined.add(item);
		}
		return combined;
	}
}
