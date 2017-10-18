package lib.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * PinyinSort接口用来对ListView中的数据根据A-Z进行排序
 * 
 * @author dongmei zhang
 * @createdate 2014-10-11
 */
public class PinyinSortUtil {
	/**
	 * 排序(前面if判断主要是将非汉字开头的数据放在前面)
	 * 
	 * @param <E>
	 * @param list
	 * @param method
	 *            根据什么排序 getSortLetters getName
	 */
	public <E> void Sort(List<E> list, final String method) {
		Collections.sort(list, new Comparator<E>() {

			@Override
			public int compare(final Object lhs, final Object rhs) {
				Collator myCollator = Collator.getInstance();
				int result = 0;
				try {
					Method m1 = ((E) lhs).getClass().getMethod(method);
					Method m2 = ((E) rhs).getClass().getMethod(method);
					Object o1 = m1.invoke(lhs);
					Object o2 = m2.invoke(rhs);
					// result = -1<--->o1<o2   result = 1<--->o1>o2
					if (o1.equals("*")) {
						result = -1;
					}else if(o2.equals("*")){
						return 1;
					} else if (o1.equals("#")) {
						result = -1;
					} else if (o2.equals("#")) {
						result = 1;
					} else {
						result = myCollator.compare(o1.toString(),
								o2.toString());
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return result;
			}

		});
	}
}
