package sanvio.libs.dbclass;

public class clsUpdateDateTime {
	private String mUpdateType,mUpdateDateTime;

	public String getUpdateType() {
		return mUpdateType;
	}

	public void setUpdateType(String pUpdateType) {
		this.mUpdateType = pUpdateType;
	}

	public String getUpdateDateTime() {
		return mUpdateDateTime;
	}

	public void setUpdateDateTime(String pUpdateDateTime) {
		this.mUpdateDateTime = pUpdateDateTime;
	}

	public clsUpdateDateTime(String pUpdateType, String pUpdateDateTime) {
		super();
		this.mUpdateType = pUpdateType;
		this.mUpdateDateTime = pUpdateDateTime;
	}
}
