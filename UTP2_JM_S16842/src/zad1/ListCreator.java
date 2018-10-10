package zad1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListCreator<T> {

	private List<T> list;

	public ListCreator(List<T> list){
		this.list = list;
	}

	public ListCreator<T> when(Selector<T> s){

		Iterator<T> iterator = list.iterator();

		while (iterator.hasNext()) {
			T e = iterator.next();
			if(!s.select(e)){
				iterator.remove();
			}
		}

		return this;
	}

	public List<T> mapEvery(Mapper<T, T> m){

		for(int n = 0; n < list.size(); n++){
			list.set(n, m.map(list.get(n)));
		}

		return list;
	}

	public static <T> ListCreator collectFrom(List<T> list){
		return new ListCreator<>(new ArrayList<>(list));
	}
}  
