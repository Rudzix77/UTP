package zad1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;


public class ListCreator<S> {

	private List<S> list;

	public ListCreator(List<S> list){
		this.list = list;
	}

	public ListCreator<S> when(Predicate<S> s){

		Iterator<S> iterator = list.iterator();

		while (iterator.hasNext()) {
			S e = iterator.next();
			if(!s.test(e)){
				iterator.remove();
			}
		}

		return this;
	}

	public <T> List<T> mapEvery(Function<S, T> m){

		List<T> out = new ArrayList<>();

		for(int n = 0; n < list.size(); n++){
			out.add(m.apply(list.get(n)));
		}

		return out;
	}

	public static <S> ListCreator<S> collectFrom(List<S> list){
		return new ListCreator<>(new ArrayList<>(list));
	}
}