/**
 *
 */
package cats.config;

/**
 * アプリケーションの設定ファイル
 *
 *
 */
public class AppSettingProperty extends ConfigBase {

	private static AppSettingProperty prop = null;
	private static final String CONFIG_NAME = "app.properties";
	private static final String CATS_UPLOAD_DIR = "cats.upload.directory";
	private static final String CATS_UPLOAD_WK_DIR = "cats.upload.work.directory";
	private static final String CATS_PROFILE_IMG_PREFIX = "cats.profile.img.prefix";


	public AppSettingProperty() throws Exception {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**インスタンスの取得
	 * @return
	 * @throws Exception
	 */
	public static AppSettingProperty getInstance() throws Exception{

		if( prop == null ){
			prop = new AppSettingProperty();
		}

		return prop;
	}

	@Override
	protected String getConfigName() {
		return CONFIG_NAME;
	}

	public String getCatsUploadDirectory(){
		return getProperty(CATS_UPLOAD_DIR);
	}
	public String getCatsUploadWorkDirectory(){
		return getProperty(CATS_UPLOAD_WK_DIR);
	}
	public String getCatsProfileImgPrefix(){
		return getProperty(CATS_PROFILE_IMG_PREFIX);
	}



}
