package zad3;

import java.util.*;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class XList<T> extends ArrayList<T> {

	public XList(T... t){
		for(T z : t){
			super.add(z);
		}
	}

	public XList(Collection c){
		super(c);
	}


	public static <T> XList of(T... t){
		return new XList(t);
	}

	public static XList of(Collection c){
		return new XList(c);
	}


	public static XList charsOf(String s){

		XList<Character> list = new XList();

		for(char c : s.toCharArray()){
			list.add(Character.valueOf(c));
		}

		return list;
	}

	public static XList tokensOf(String s){
		return new XList(s.split(" "));
	}

	public static XList tokensOf(String s, String sep){
		return new XList(s.split(sep));
	}


	public XList<T> union(T... e) {

		XList<T> list = new XList<>(this);

		for(T t : e){
			list.add(t);
		}

		return list;

	}

	public XList<T> union(Collection<T> c) {
		return union((T[]) c.toArray());
	}

	public XList<T> diff(Collection<T> c) {
		XList<T> list = new XList(this);

		Iterator<T> it = list.iterator();

		while(it.hasNext()){
			if(c.contains(it.next())){
				it.remove();
			}
		}

		return list;
	}

	public XList<T> unique() {
		return new XList(this.stream().distinct().collect(Collectors.toList()));
	}


	public XList<XList<T>> combine(){

		XList<XList<T>> list = new XList<>();

		int amount = 1;

		for(int n = 0; n < this.size(); n++){
			amount *= ((List<T>) this.get(n)).size();
		}

		int helper[] = new int[this.size()];
		int coef[] = new int[this.size()];
		coef[0] = 1;
		int pointer[] = new int[this.size()];

		for(int n = 0; n < amount; n++){
			XList<T> box = new XList();
			list.add(box);
		}

		for(int z = 0; z < this.size(); z++){

			List<T> subset = (List) this.get(z);

			for(int n = 0; n < amount; n++){
				list.get(n).add(subset.get(helper[z]));

				pointer[z]++;

				if(coef[z] == pointer[z]){
					pointer[z] = 0;
					helper[z]++;
				}

				if(helper[z] == subset.size()){
					helper[z] = 0;

					if(z+1 < coef.length){
						if(coef[z+1] == 0){
							coef[z+1] = n+1;
						}
					}
				}
			}

		}

		return list;
	}


	public String join(String sep){
		return this.stream().map(Object::toString).collect(Collectors.joining(sep));
	}

	public String join(){
		return join("");
	}

	public <S> XList<S> collect(Function<T,S> f){
		XList<S> list = new XList();

		for(T s : this){
			list.add(f.apply(s));
		}

		return list;
	}

	public void forEachWithIndex(BiConsumer<T, Integer> c){
		for(int n = 0; n < this.size(); n++){
			c.accept(this.get(n), n);
		}
	}

	public String toString() {
		return Arrays.toString(super.toArray());
	}
}

