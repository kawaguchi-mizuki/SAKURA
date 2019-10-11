package cats.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 設定ファイルベースクラス
 *
 *
 */

public abstract class ConfigBase {

	private Properties config = new Properties();



	public ConfigBase() throws Exception{
		InputStream inputStream;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream(getConfigName());
			config.load(inputStream);

		} catch (FileNotFoundException e) {
			// システムエラー
			throw new Exception(e);
		}

	}

	/**
	 * コンフィグファイルから情報取得する
	 * @return
	 */
	public String getProperty(String paramName){

		return config.getProperty(paramName);
	}


	/**
	 * コンフィグファイルから情報取得する
	 * @return
	 */
	public Float getProperty(String paramName,Float defaultVal){
		Float retVal = defaultVal;

		try{
			retVal = Float.parseFloat(config.getProperty(paramName));
		}catch(NumberFormatException e){
			retVal = defaultVal;
		}

		return retVal;
	}

	public Integer getProperty(String paramName,Integer defaultVal) {
		Integer retVal = defaultVal;

		try{
			retVal = Integer.parseInt(config.getProperty(paramName));
		}catch(NumberFormatException e){
			retVal = defaultVal;
		}

		return retVal;

	}


	abstract protected String getConfigName();
}
