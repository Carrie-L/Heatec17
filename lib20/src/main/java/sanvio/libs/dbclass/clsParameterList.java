package sanvio.libs.dbclass;

public class clsParameterList {
	private String mParameterName;
	private Object mParameterValue;
	private Boolean mIsCustomerObj = false;

	public clsParameterList(String pParameterName, Object pParameterValue) {
		setParameterName(pParameterName);
		setParameterValue(pParameterValue);
	}

	public clsParameterList(String pParameterName, Object pParameterValue, Boolean pIsCustomerObj) {
		this(pParameterName, pParameterValue);
		setIsCustomerObj(pIsCustomerObj);
	}

	public String getParameterName() {
		return mParameterName;
	}

	public void setParameterName(String pParameterName) {
		this.mParameterName = pParameterName;
	}

	public Object getParameterValue() {
		return mParameterValue;
	}

	public void setParameterValue(Object pParameterValue) {
		this.mParameterValue = pParameterValue;
	}

	/**
	 * @return the mIsCustomerObj
	 */
	public Boolean getIsCustomerObj() {
		return mIsCustomerObj;
	}

	/**
	 * @param mIsCustomerObj
	 *            the mIsCustomerObj to set
	 */
	public void setIsCustomerObj(Boolean mIsCustomerObj) {
		this.mIsCustomerObj = mIsCustomerObj;
	}
}