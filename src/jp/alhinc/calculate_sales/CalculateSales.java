package jp.alhinc.calculate_sales;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
				//注意点：for文の中で「Listつくります」の宣言すると、「Listをつくる作業」も繰り返される。すなわち、rcdFilesが繰り返し停止の指示があるまでずっと作り続けられる
				//だから、Listは1個しか要らないしそこにいれてくのでfor文の外に書く。
				//かつ、addメソッドより下に書くと「rcdFilesってなんすか？」とコンピューターは困ってしまう。
				//だから、addより上に書く。
				//for文の外、かつ、addより上、であるところに宣言するのが正しい。
				//それがどこかっっていうと、File[] files = new File("C:\\Users\\trainee1209\\Desktop\\売上集計課題").listFiles();の下！
				rcdFiles.add(files[i]);

			}

		}

		//ここから2-2 ファイルの読込
		//やりたいこと：①売上ファイルを読み込んで格納して、②読み込んだ売上ファイルから「支店コード」「売上額」を取り出して、③売上額を加算する
		//っていうのをファイル数分だけ繰り返したい！！

		//まず、変数の数だけ特定の作業を繰り返してほしいことを宣言するため「for文」を使う
		//ここでは、変数＝ファイル数 であり、特定の作業というのは、for文内のもっと後ろで行うので一旦無視
		for(int i = 0; i < rcdFiles.size(); i++) {

			BufferedReader br = null;

			try {
				//なにを繰り返しましょうか？の状態なので、これからやりたいことの、
				//①売上ファイルを読み込んで格納して、②読み込んだ売上ファイルから「支店コード」「売上額」を取り出して、③売上額を加算する
				//①-③のうちの、①だけを初めに行う
				//どうやって？：BufferedReaderクラスのreadlineメソッドを使って情報を読み込んだのちに格納する
				//なぜbrを使う？：BufferedReaderクラスは、ファイル情報を読み込める仕組み。
				//また、売上ファイルは改行区切りであるため、1文字ずつ読み込むFileReaderderのreadメソッドではなくBufferedReaderのreadlineメソッドを使う

				//ファイルのパス＝使いたいファイルの住所
				//それを使ってどうしたい？ = BufferedReaderクラスのreadlineメソッドを使って一行ずつ読み込んでほしい
				//File型のfileっていう変数に代入します = Fileっていうのは（売上集計課題フォルダの中の、rcdFilesを1個ずつ取り出して名前をきいたもの）
				File file = new File("C:\\Users\\trainee1209\\Desktop\\売上集計課題", rcdFiles.get(i).getName());
				//fileReaderをつくる
				FileReader fr = new FileReader(file);
				//brを使うには、面倒だが1回frを作らないといけない。イメージは「スキルアップ」
				br = new BufferedReader(fr);

				//使いたいメソッドを↑で指示することができた。じゃあ次。いつまで一行ずつ読み込みますか？の指示待ち状態
				//なにを、いつまで（行を = 一行ずつ読み込むのが = なくなるまで）やってほしいです
				String line;
				List<String>loadedstr = new ArrayList<>();
				//～まで(lineていう変数に = readlineメソッドで読み込んだ変数を代入する、nullでなければ
				while((line = br.readLine()) != null) {

					//↑だけだと、「読んだものが宙ぶらりん状態」。だから格納しないといけない
					//配列かlistか？  list。nullって私はわかるけど、コンピューターはいつまでがnullかわからないので文字列があるだけ無限に
					//↑でlistを作れた。このlistに、読み込んだ変数である「line」を入れて、と指示する
						loadedstr.add(line);
				}

				//ここから2-2 型の変換
				//ここまでで、読み込むことだけできている
				//このあとどんな流れ？ → 最終的に「加算できるようにしたい」ので、
				//①まず、Stringとして扱われている売上額をLongに変換して格納しなおす、
				//なぜ？ → Mapは「key：String、Value：int」じゃないとだめだから。かつ、売上額は大きくなる可能性があるためintよりLongが適切
				//どうやって？ → Long型のparseLongメソッドを使って変換する
				//②そのうえで、変換した売上額をMapからもってくる
				//どうやって？ → keyとValueの関係性を使って、鍵を開けるイメージ？
				//③そしてはじめて計算できるようになるため、加算する

				//①をやっていく。売上額がStringになっているので、Longに変換する
				//売上金額は、読込時に(1)に入っている。支店コードが変わろうがこれは同じ。てことは支店コードは(0)に入っている
				//売上額さんがご入居されている住所を、()にいれる。参照するということ
				long fileSale = Long.parseLong(loadedstr.get(1));

				//Map(HashMap)から売上額を取得して、加算することを一気に行う
				//Long saleAmount = 売上⾦額を⼊れたMap.get(⽀店コード) + long に変換した売上⾦額; に倣って書く
				//saleAmountっていうのは、売上金額を入れたmapです、支店コードがkeyでlongに変換した売上額がvalue
				Long saleAmount = branchSales.get(loadedstr.get(0)) + fileSale;
				//支店コードと売上額を追加するためのmap：branchSalesに、(支店コードを表す第一引数,売上額を表す第二引数)をぶちこむput
					branchSales.put(loadedstr.get(0), saleAmount);


			} catch(IOException e) {
				System.out.println(UNKNOWN_ERROR);
				return;
			} finally {
				// ファイルを開いている場合
				if(br != null) {
					try {
						// ファイルを閉じる
						br.close();
					} catch(IOException e) {
						System.out.println(UNKNOWN_ERROR);
						return;
					}
				}
			}
		}


		// 支店別集計ファイル書き込み処理
		if(!writeFile(args[0], FILE_NAME_BRANCH_OUT, branchNames, branchSales)) {


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
		// 以下に書き込み処理を作成してください。(処理内容3-1)

		//やること：①ファイルを作って、②書き込んで、③書き込みの例外処理して、④出力
		//まず①をする。fileを新しく作るんだけど、（）に作ってね
		File file = new File("\"C:\\\\Users\\\\trainee1209\\\\Desktop\\\\売上集計課題\"");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(bw);

		//Keyが取得できればMapのgetメソッドを使⽤してValueも取得できるため、どちらかのMapから全てのKeyを取得する必要がある
		//そのkeyの文だけ繰り返してね、を指示する
		//拡張for文を使う理由は? → 全要素を順番に取り出したいから。keyを使ってそれぞれのvalueを全部取得したいから拡張for文を使う

		//やりたいこと：①branchNamesからkeyを使ってvalueを取り出す、②取り出したkeyとvalueを書き込む、③branchSalesからkeyを使ってvalueのみ取り出す、④改行する
		//というのを繰り返したい！
		//まず繰り返すことを宣言する。繰り返します（branchNamesっていうmapの、string型のkeyを1個ずつ）

		for

		try {

		for (String key : branchNames.keySet()) {

			//①をやる。map.get
			String value1;
			branchNames.get(key + value1);

				bw.write(key);
				bw.write(value1);

		}

		    //②をやる。まず繰り返すことを宣言する
		for (String key : branchSales.keySet()) {

		     String value2;
		     branchSales.get(key + value2);
			     bw.write(value2);

			     bw.newLine();

		}

		} catch(IOException e) {
			System.out.println(UNKNOWN_ERROR);
			return false;

		} finally {
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

		}








		//書き込みたい支店コード、支店名、合計金額っていうのはこうやって取得してね

		//try ：ファイルを作成し、書き込む処理
		//catch ：エラーメッセージの表⽰
		//finally ：ファイルを開いた場合は、ファイルを閉じる処理


		return true;
	}

}
