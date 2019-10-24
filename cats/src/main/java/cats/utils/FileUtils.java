package cats.utils;

import java.io.File;

/**ファイルのユーティリティ
 *
 *
 */
public class FileUtils {


	/** ディレクトリ作成
	 * @param dir
	 */
	public static void makeDir(String dir){
		//Fileオブジェクトを生成する
		File f = new File(dir);

		if (!f.exists()) {
			//フォルダ作成実行
			f.mkdirs();
		}
	}
	/**
	 * パスからファイル名を取得する
	 * @param path
	 * @return
	 */
	public static String getFileNameFromPath(String path){

		File f = new File(path);

		return f.getName();
	}

}
