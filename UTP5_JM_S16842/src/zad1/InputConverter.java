package zad1;

import java.util.function.Function;

public class InputConverter<E> {

	E element;

	public InputConverter(E element) {
		this.element = element;
	}

	public <R> R convertBy(Function... func){
		R tmp = null;

		for(int n = 0; n < func.length; n++){
			tmp = (R) func[n].apply((n == 0) ? element : tmp);
		}

		return tmp;
	}


}
