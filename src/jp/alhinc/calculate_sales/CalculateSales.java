package jp.alhinc.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateSales {

	// 支店定義ファイル名
	private static final String FILE_NAME_BRANCH_LST = "branch.lst";

	// 支店別集計ファイル名
	private static final String FILE_NAME_BRANCH_OUT = "branch.out";

	// エラーメッセージ
	private static final String UNKNOWN_ERROR = "予期せぬエラーが発生しました";
	private static final String FILE_NOT_EXIST = "支店定義ファイルが存在しません";
	private static final String FILE_INVALID_FORMAT = "支店定義ファイルのフォーマットが不正です";

	/**
	 * メインメソッド
	 *
	 * @param コマンドライン引数
	 */
	public static void main(String[] args) {
		// 支店コードと支店名を保持するMap
		Map<String, String> branchNames = new HashMap<>();
		// 支店コードと売上金額を保持するMap
		Map<String, Long> branchSales = new HashMap<>();

		// 支店定義ファイル読み込み処理
		if(!readFile(args[0], FILE_NAME_BRANCH_LST, branchNames, branchSales)) {
			return;
		}

		// ※ここから集計処理を作成してください。(処理内容2-1、2-2)
		//listFilesを使⽤してfilesという配列に、
		//指定したパスに存在する全てのファイル(または、ディレクトリ)の情報を格納します。
		File[] files = new File("C:\\Users\\trainee1209\\Desktop\\売上集計課題").listFiles();
		List<File> rcdFiles = new ArrayList<>();
		
		//filesの数だけ見るのを繰り返すことで、
		//指定したパスに存在する全てのファイル(または、ディレクトリ)の数だけ繰り返されます。
		//0000001.rcd-00000005.rcdとbranch.lstを一つずつ見るのをくりかえして = for文
		//以下のfor文は、「繰り返します」ことだけ言ってる
		for(int i = 0; i < files.length ; i++) {

			//左辺は「変数の宣言＝何型の変数か」、右辺は「代入する内容。それがなんであるか。今だったら、"00000001.rcd"が右辺に入っている」
			String fileName = files[i].getName() ;
			//ファイル名を取得する = 〇〇をしたいから、それするのに必要な「あなたの名前 = ファイル名」を教えて！という作業
			//以下のgetNameメソッドは、「あなたのお名前 = ファイル名伺います～」しか聞いてない

			//matches を使⽤してファイル名が「数字8桁.rcd」なのか判定します。
			//「8桁の数字+.rcd」で条件付けをして判定したい。全部文字列だから、String型になる
			if(fileName.matches("[0-9]{8}+.rcd$")) {
		    //trueの場合の処理を書きましょう
			//trueの場合 = 「8桁の数字＋.rcd」と合致する場合
			//ここまでは「判定しただけ」。ここから先で「判定したファイルを配列」する

				//やりたいこと：matches文をtrueで通過したファイルを「売上ファイル」として保持する
				//すなわち、シンプルに「00000001.rcd-00000005.rcdを、ArrayListに追加する」指示を出せばいい
				//宣言をする。↓
				//「ArrayListのaddメソッドを使って、「売上ファイル」をList（＝rcdFiles)に追加するよ！」
				rcdFiles.add(files[i]);

			}

		}



		// 支店別集計ファイル書き込み処理
		if(!writeFile(args[0], FILE_NAME_BRANCH_OUT, branchNames, branchSales)) {
			return;
		}

	}

	/**
	 * 支店定義ファイル読み込み処理
	 *
	 * @param フォルダパス
	 * @param ファイル名
	 * @param 支店コードと支店名を保持するMap
	 * @param 支店コードと売上金額を保持するMap
	 * @return 読み込み可否
	 */
	private static boolean readFile(String path, String fileName, Map<String, String> branchNames, Map<String, Long> branchSales) {
		BufferedReader br = null;

		try {
			File file = new File(path, fileName);
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);

			//読み込んだものを格納する（一行分だけ）
			String line;
			// 一行ずつ読み込む
			while((line = br.readLine()) != null) {
				// ※ここの読み込み処理を変更してください。(処理内容1-2)

				//読み込んだ一行をsplitメソッドで分割したものをitemsに格納（2itemになっている。1個目が支店コード（items[0]、2個目が支店名(items[1]）
				 String[] items = line.split(",");

				//Mapに追加する2つの情報を putの引数として指定します。
				  branchNames.put(items[0], items[1]);
				  branchSales.put(items[0], 0L);


				}

		} catch(IOException e) {
			System.out.println(UNKNOWN_ERROR);
			return false;
		} finally {
			// ファイルを開いている場合
			if(br != null) {
				try {
					// ファイルを閉じる
					br.close();
				} catch(IOException e) {
					System.out.println(UNKNOWN_ERROR);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 支店別集計ファイル書き込み処理
	 *
	 * @param フォルダパス
	 * @param ファイル名
	 * @param 支店コードと支店名を保持するMap
	 * @param 支店コードと売上金額を保持するMap
	 * @return 書き込み可否
	 */
	private static boolean writeFile(String path, String fileName, Map<String, String> branchNames, Map<String, Long> branchSales) {
		// ※ここに書き込み処理を作成してください。(処理内容3-1)

		return true;
	}

}
