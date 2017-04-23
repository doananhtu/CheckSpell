package myapp.anhtu.com.checkspell.utility;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import myapp.anhtu.com.checkspell.database.ExceptionDB;
import myapp.anhtu.com.checkspell.database.ToHopAmGiua;
import myapp.anhtu.com.checkspell.entity.Page;
import myapp.anhtu.com.checkspell.entity.Result;

/**
 * Created by anhtu on 4/21/2017.
 */

public class CheckSpellUtils {
    public CheckSpellUtils() {
    }
    /* Âm tiết = [Âm đầu]<Tổ hợp âm giữa>[Âm cuối]
       Trong đó những thành phần nằm trong cặp dấu <> là bắt buộc phải có
       những thành phần nằm trong cặp dấu [ ] thì có thể có hoặc không.
       Tiếng việt có 12 nguyên âm đơn không dấu
       32 nguyên âm đôi không dấu
       13 nguyên âm ba không dấu
       */

    //Bảng chữ cái
    public static final String ALPHABET = "0123456789ABCDEGHIKLMNOPQRSTUVXYẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊ" +
            "ỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴ";

    //Các nguyên âm và nguyên âm có dấu trong tiếng Việt
    public static final String VIETNAMESE_DIACRITIC_CHARACTERS = "AẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠEẾỀỂỄỆÊÉÈẺẼẸIÍÌỈĨỊ" +
            "OỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌUỨỪỬỮỰƯÚÙỦŨỤYÝỲỶỸỴ";

    //Có 26 loại âm đầu trong cấu tạo âm tiết.
    public static final String[] AM_DAU = {"b","c","ch","d","đ","g","gh","h","k","kh","l","m","n","ng",
            "ngh","nh","p","ph","q","r","s","t","th","tr","v","x"};

    //Có 8 loại âm cuối trong cấu tạo âm tiết.
    public static final String[] AM_CUOI = {"c","ch","m","n","ng","nh","p","t"};

    public ArrayList<Result> checkSpell(ArrayList<Page> listPage, Context context, int index, int num){

        // Lấy dữ liệu lần đầu về tổ hơp âm giữa và ngoại lệ. Dữ liệu sẽ được cập nhật trong quá trình sử dụng.
        ToHopAmGiua db = new ToHopAmGiua(context);
        ExceptionDB dbEx = new ExceptionDB(context);
        if(db.getAllAmGiua().size() == 0){
            Toast.makeText(context,"Loading data...",Toast.LENGTH_SHORT).show();
            ArrayList<String> amGiua = loadingData(context);
            for(String item: amGiua){
                db.addAmGiua(item);
            }
        }

        //Số trang cần đọc
        if(index == 0) index++;
        if(index >= listPage.size()) index = 1;
        if(num == 0) num++;
        int loop = index + num;
        if( (index+num) >= listPage.size()){
            loop = listPage.size();
        }
        ArrayList<Result> result = new ArrayList<>();
        for(int j=index-1; j<loop; j++){
            //Tách ra các từ riêng biệt theo khoảng trắng và \n
            String words[] = listPage.get(j).getContent().split("[ \n]");
            for(int i=0; i<words.length; i++){
                String pattern = "[^"+ ALPHABET +"]"; //Loại bỏ các kí tự không có trong bảng chữ cái
                Pattern pt = Pattern.compile(pattern, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
                Matcher mc = pt.matcher(words[i]);
                while (mc.find()){
                    words[i] = words[i].replace(mc.group(),"");
                }
            }

            // Kiểm tra luật
            for(int i=0; i<words.length; i++){
                if(words[i].equals("")){
                    continue;
                }
                boolean check = true;
                if(rule1(words[i])){ // string is numeric?
                    continue;
                } //Nếu từ không phải là chữ số thì từ đó không được chứa cả chữ cái và chữ số
                else{
                    // Kiểm tra luật số 2
                    check = rule2(words[i]);
                    if(check == false){
                        result.add(new Result(words[i],listPage.get(j).getPageNumber()));
                        continue;
                    }
                    //Khi tu chi có kí tự chữ cái thì kiểm tra có tổ hợp nguyên âm hay không
                    //Kiem tra luat số 3
                    check = rule3(words[i]);
                    if(check == false){
                        result.add(new Result(words[i],listPage.get(j).getPageNumber()));
                        continue;
                    }
                    //Khi từ đã có tổ hợp nguyên âm thì kiểm tra âm đầu và âm cuối hợp lệ hay không
                    //Kiem tra luat số 4
                    check = rule4(words[i]);
                    if(check == false){
                        result.add(new Result(words[i],listPage.get(j).getPageNumber()));
                        continue;
                    }
                    /* Kiểm tra tổ hợp âm giữa có trong cơ sở dữ liệu không?*/
                    check = rule5(words[i],db);
                    if(check == false){
                        result.add(new Result(words[i],listPage.get(j).getPageNumber()));
                        continue;
                    }

                    /* Cuối cùng kiểm tra trường hợp ngoại lệ*/
                    check = rule6(words[i],dbEx);
                    if(check == false){
                        result.add(new Result(words[i],listPage.get(j).getPageNumber()));
                        continue;
                    }
                }
            }
        }

        if (result.size() == 0) {
            result.add(new Result("Không tìm thấy kết quả nào!",0));
        }
        return result;
    }

    //Tạo dữ liệu cho tổ hợp âm giữa.
    public ArrayList<String> loadingData(Context context){
        ArrayList<String> record = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("data.txt")));
            String line;
            while ((line = br.readLine()) != null) {
                record.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return record;
    }

    public String addingData(String word){
        String amGiua = "";
        String pattern = "["+ VIETNAMESE_DIACRITIC_CHARACTERS +"]";
        Pattern pt = Pattern.compile(pattern, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher mc = pt.matcher(word);
        while(mc.find()){
            amGiua += mc.group();
        }
        return amGiua;
    }

    //Luật xây dựng trên cấu tạo âm tiết của tiếng Việt
    //Luật số 1: string is numeric?
    public boolean rule1(String word){
        try {
            int i = Integer.parseInt(word);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    /*Luật số 2: không được chứa cả chữ cái và chữ số. */
    public boolean rule2(String word){
        String pattern = "[0123456789]";
        Pattern pt = Pattern.compile(pattern, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher mc = pt.matcher(word);
        if(mc.find()){
            return false;
        }
        else
            return true;
    }

    /*Luật số 3: Một từ phải có nguyên âm */
    public boolean rule3(String word){
        String pattern = "["+ VIETNAMESE_DIACRITIC_CHARACTERS +"]";
        Pattern pt = Pattern.compile(pattern, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher mc = pt.matcher(word);
        if(mc.find()){
            return true;
        }
        else
            return false;
    }

    /*Luật số 4: Kiểm tra âm đầu và âm cuối*/
    public boolean rule4(String word){
        String amDau = null;
        String amCuoi = null;
        boolean check1 = false, check2 = false;
        String wordUpcase = word.toUpperCase();
        // Tách âm tiết
        String[] amTiet = wordUpcase.split("["+ VIETNAMESE_DIACRITIC_CHARACTERS +"]");
        if(amTiet.length == 0)
            return true;
        if( !amTiet[0].isEmpty() ){
            amDau = amTiet[0];
        }
        if(!amTiet[amTiet.length-1].isEmpty()){
            amCuoi = amTiet[amTiet.length-1];
        }
        if(amTiet.length == 1)
            amCuoi = null;
        if(amDau == null) check1 = true;
        if(amCuoi == null) check2 = true;
        //Kiêm tra âm đầu có đúng không.
        if(amDau != null){
            for(int i=0; i< AM_DAU.length; i++){
                if(AM_DAU[i].toUpperCase().equals(amDau)){
                    check1 = true;
                }
            }
        }
        if(amCuoi != null) {
            for (int i = 0; i < AM_CUOI.length; i++) {
                if (AM_CUOI[i].toUpperCase().equals(amCuoi)) {
                    check2 = true;
                }
            }
        }
        if(check1 && check2){
            return true;
        }else
            return false;
    }

    /* Luật số 5: So khớp tổ hợp âm giữa với cơ sở dữ liệu.*/
    public boolean rule5(String word, ToHopAmGiua db){
        String amGiua = "";
        ArrayList<String> listAmGiua = db.getAllAmGiua();
        String pattern = "["+ VIETNAMESE_DIACRITIC_CHARACTERS +"]";
        Pattern pt = Pattern.compile(pattern, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher mc = pt.matcher(word);
        while(mc.find()){
            amGiua += mc.group();
        }
        for(String item: listAmGiua){
            if(amGiua.toUpperCase().equals(item)){
                return true;
            }
        }
        return false;
    }

    /* Luật số 6: Kiểm tra từ có rơi vào trường hợp ngoại lệ không. */
    public boolean rule6(String word, ExceptionDB db){
        ArrayList<String> listEx = db.getAllEx();
        if(listEx.size() == 0) return true;
        for(String item: listEx){
            if(item.equals(word.toUpperCase())){
                return false;
            }
        }
        return true;
    }
}
