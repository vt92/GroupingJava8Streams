import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class StringOccurenceInList {
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		list.add("abc");list.add("def");list.add("abc");list.add("def");
		list.add("abc");list.add("def");
		list.add("ghi");list.add("ghi");
		list.add("xyz");list.add("def");

		Map<String, Long> count= list.stream().collect(Collectors.groupingBy(e->e, Collectors.counting()));
		System.out.println(count);

	}
}
