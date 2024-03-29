package algorithms.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CartesianProductHelper {

	static public <T> List<List<T>> compute(Collection<List<T>> lists) {
		List<List<T>> combinations = Arrays.asList(Arrays.asList());
		for (List<T> list : lists) {
			final List<List<T>> extraColumnCombinations = new ArrayList<>();
			for (List<T> combination : combinations) {
				for (T element : list) {
					final List<T> newCombination = new ArrayList<>(combination);
					newCombination.add(element);
					extraColumnCombinations.add(newCombination);
				}
			}
			combinations = extraColumnCombinations;
		}
		return combinations;
	}
}
