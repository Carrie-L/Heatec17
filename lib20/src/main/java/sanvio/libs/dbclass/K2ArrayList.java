package sanvio.libs.dbclass;

import java.util.ArrayList;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class K2ArrayList<E> extends ArrayList<E> implements KvmSerializable{
	private Class<?> mMemberClass;
	private String mMemberName;
	public  K2ArrayList(String pMemberName,Class<?> pMemberClass){
		mMemberName=pMemberName;
		mMemberClass=pMemberClass;
	}
	private static final long serialVersionUID = 1L;

	//	public List<clsTest> list = new ArrayList<clsTest>();
	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		return this.get(arg0);
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return this.size();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		arg2.type =mMemberClass;
		arg2.name = mMemberName;
		arg2.setValue(this.get(arg0));
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		E aObject=(E) arg1;
		this.add(arg0, aObject);
	}

}
