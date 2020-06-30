package com.example.e_support.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Set;

public class DBHelper extends SQLiteOpenHelper {
    private static final String CREATE_SPECIALIST = "create table specialist(id integer primary key," +
            "name text,honor text,place text,field text,description text,phone text,account text)";
    private static final String CREATE_TECH = "create table tech(id integer primary key," +
            "techname text,techdomain text,suitarea text,linkman text,tel text,org text," +
            "techowner text,techbrief text)";
    private static final String CREATE_INDUSTRY = "create table industry(id integer primary key," +
            "corpname text,industryname text,place text,devtrend text)";
    private Context mContext;
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INDUSTRY);
        db.execSQL(CREATE_SPECIALIST);
        db.execSQL(CREATE_TECH);
        ContentValues specialistValues = new ContentValues();
        specialistValues.put("name","张三");
        specialistValues.put("honor","副高级");
        specialistValues.put("place","洪湖市农业畜牧水务局");
        specialistValues.put("field","农业领域");
        specialistValues.put("phone","123456789");
        specialistValues.put("description","积极参加完成科技局安排大白菜栽培技术500亩的实验示范，" +
                "参与2019年农业科技培训工作，参与培训班8期，完成新型农民培训200人，完成全县农业三项" +
                "补贴兑付工作全县面积33138.15亩，兑付资金2236825.13元。");
        for(int i = 0;i<4;i++){
            specialistValues.put("id",i);
            specialistValues.put("account","rongwenbin"+i);
            db.insert("specialist",null,specialistValues);
        }
        specialistValues.clear();
        specialistValues.put("name","李四");
        specialistValues.put("honor","副高级");
        specialistValues.put("place","恩施县农业畜牧水务局");
        specialistValues.put("field","畜牧兽医");
        specialistValues.put("phone","123456789");
        specialistValues.put("description","主要开展了畜禽防治、改良、动物防疫法律法规宣传、畜牧业" +
                "开展等工作，促进了本县畜牧业稳定、健康发展。参与县局主持的南江黄羊改良工作，改良" +
                "黄羊一千多只。");
        for(int i = 4;i<8;i++){
            specialistValues.put("id",i);
            specialistValues.put("account","wangguangqiang"+i);
            db.insert("specialist",null,specialistValues);
        }
        specialistValues.clear();
        specialistValues.put("name","王五");
        specialistValues.put("honor","初级");
        specialistValues.put("place","建南县环境监测站");
        specialistValues.put("field","环境检测");
        specialistValues.put("phone","123456789");
        specialistValues.put("description","熟悉环境检测相关工作，能够独立开展实验室分析工作" +
                "，对水和废水、空气和废气、噪声等项目能够有效的开展分析工作。");
        for(int i = 8;i<12;i++){
            specialistValues.put("id",i);
            specialistValues.put("account","chenmingkun"+i);
            db.insert("specialist",null,specialistValues);
        }
        ContentValues techValues = new ContentValues();
        techValues.put("techname","麦子");
        techValues.put("techdomain","饲草 优良品种");
        techValues.put("suitarea","二半山及高山地区");
        techValues.put("linkman","章三");
        techValues.put("tel","123456789");
        techValues.put("org","蕲春市农业农村局畜牧站");
        techValues.put("techowner","黎四");
        techValues.put("techbrief","该品种为一年生或越年生禾本科草。根系发达，茎直立丛生，株高60~110cm，茎粗0.5~0.8cm。叶鞘闭合，被柔毛；叶舌长约2mm;叶片长30~45cm，宽0.85~1.45cm，散生柔毛。圆锥花序，长15~25cm，小穗两侧极压扁；千粒重8.5~12.3g。染色体2n=28。喜温暖湿润气候，适应性强，产量高，生长快，水分多，同时适口性好，营养丰富，是－种非常适合冬春季牛羊补饲的优质牧草。生育期长达230~265天，可刈割鲜草3~5次，年鲜草产量55t/hm^2~80 t/hm^2，种子产量1806 75~2599.95kg/hm^2。粗蛋白含量占干物质的14.52%，粗脂肪占干物质的2.15%，酸性涤纤维37.98%，中性涤纤维53.78%，灰分7.72%，钙0.7%，磷0.39%，钾3.58%，是一种优良的禾本科牧草。具有较强适应性，在高海拔1500m~2300m地区生长良好，其产量高，生长快，水分多，同时适口性好，营养丰富，是一种非常适合冬春季作为牛羊补饲的优质牧草或人工草地混播放牧牧草。");
        for(int i = 0;i<4;i++){
            specialistValues.put("id",i);
            db.insert("tech",null,techValues);
        }
        techValues.clear();
        techValues.put("techname","魔芋软腐病防治方法");
        techValues.put("techdomain","魔芋 种养技术");
        techValues.put("suitarea","全区");
        techValues.put("linkman","晚晚");
        techValues.put("tel","123456789");
        techValues.put("org","恩施县农业农村局畜牧站");
        techValues.put("techowner","马刘");
        techValues.put("techbrief","防治方法:(1)选择不积水地栽植，井做到深耕细耙，高垄深沟，小块种植。(2)做好选种、晒种和浸种，精选健芋晒1~2天后用硫酸链霉素500ppm浸1小时，晾千下种。(3)加强检查，及时施药控制。发现中心病株立即挖除，并用链霉素400ppm灌淋病穴及周围植株2次，每株每次0.5升药液或用链霉素10000ppm注射植株每株每次注入3~4毫升药液。");
        for(int i = 4;i<8;i++){
            specialistValues.put("id",i);
            db.insert("tech",null,techValues);
        }
        techValues.clear();
        techValues.put("techname","药用栀子");
        techValues.put("techdomain","中药材 种养技术");
        techValues.put("suitarea","全区");
        techValues.put("linkman","牛期");
        techValues.put("tel","123456789");
        techValues.put("org","宣恩县中药材种植专业合作社");
        techValues.put("techowner","宣恩县中药材种植专业合作社");
        techValues.put("techbrief","1、栀子、枳壳培育种苗路线\n" +
                "筛选种子->表面消毒-＞清水冲洗-＞浸泡-＞播种\n" +
                "->驯化移栽。\n" +
                "2、栀子、枳壳苗木药材净作高产栽培技术路\n" +
                "线：\n" +
                "选择测定土壤一＞选地、整地一＞选苗一＞栽植药材苗->田间管理->生物防治病虫害->中耕除草一＞定期施肥->采收加工一包装销售（栀子两年后开始挂果并可连续收获30年，枳壳三年后开始挂果，并可连续收获70年以上）\n" +
                "3、栀子、枳壳苗木药材与白芨、半夏、黄芩、桔梗等矮杆药材套作相关技术的研究及实施路线\n" +
                "调查项目实施区域经济作物种类一＞选择确定试验研究地点一＞编制实施方案－确定套作品种密度->田间管理->科学施肥->生物病虫害防治等技术措施一＞定期跟踪记录套作品种生长状况、优化调整试验方案，开展大面积套作实验研究一＞初步形成套作技术体系。");
        for(int i = 8;i<12;i++){
            techValues.put("id",i);
            db.insert("tech",null,techValues);
            Log.e("DBinsert",String.valueOf(i));
        }
        ContentValues industryValues = new ContentValues();
        industryValues.put("corpname","湖北省生态农业发展有限公司");
        industryValues.put("industryname","牛");
        industryValues.put("place","恩施州 建始县");
        industryValues.put("devtrend","1.黑山羊养殖场要想提高养殖的效益，必须要加强精细化管理，养殖户们要从育种方向上，提高羊群的繁殖能力上解决问题，可以运用先进的养殖技术和管理手段提高黑山羊的产羔率，再就是养殖场一定要合理配备养殖人员，减少不必要的人员开支，还可以最大限度地提高员工的养殖积极性。在饲料喂养上，\n" +
                "定要减少浪费，充分利用当地秸秆等农作物做为黑山羊的主要饲养草料。\n" +
                "\n" +
                "2.黑山羊的市场需求非常的大，虽然黑山羊羊肉价格比较高，但是随着人们经济生活水平的提高，尤其是近几年，羊肉火锅、羊肉串也带动了大批的消费人群，再就是黑山羊大部分都是天然养殖，生态养殖没有污染，食用更为放心，黑山羊有广阔的市场空间，大部分消费者还是比较喜欢食用黑山羊羊肉的。\n" +
                "\n" +
                "3.农村现在有越来越多的空闲场地可以利\n" +
                "用，大家可以养殖一些家畜以此来提高经济收入，充分利用好本地秸秆资源，农村本来就是以种地和养殖为主，拥有天然的养殖环境，再就是政府部门现在都大力扶持、重视农业农村以及畜牧业发展，养殖户们还能享受一定的政府补贴，现在农村的道路交通也比较发达，如果需要出售黑山羊的话，给收购户一个电话就会上门进行收购，养殖户们不用担心卖不出去的问题，可谓是天时地利人和。\n");
        for(int i = 0;i<4;i++){
            industryValues.put("id",i);
            db.insert("industry",null,industryValues);
        }
        industryValues.clear();
        industryValues.put("corpname","湖北茶叶有限公司");
        industryValues.put("industryname","茶叶");
        industryValues.put("place","恩施州 建南县");
        industryValues.put("devtrend","湖北茶叶有限公司是国家级" +
                "农业产业化龙头企业，带领周边农户增收致富；通过技术集成创新支撑产业链发展，实现茶叶品种良种化、栽培技术生态化、加工工艺现代化、副产物利用高效化，提升川东北地区的富硒茶产业的整体发展水平，实现万源茶叶加工、开发企业和茶叶种植户增收致富，最终实现万源富硒茶叶产业跨越式发展。\n");
        for(int i = 4;i<8;i++){
            industryValues.put("id",i);
            db.insert("industry",null,industryValues);
        }
        industryValues.clear();
        industryValues.put("corpname","湖北省农业开发有限公司");
        industryValues.put("industryname","花生");
        industryValues.put("place","恩施州 宣恩县");
        industryValues.put("devtrend","公司将扩大花生种植规模，对花生进行深加工，做成即食食品，如多口味花生、卤花生、花生酱等");
        for(int i = 8;i<12;i++){
            industryValues.put("id",i);
            db.insert("industry",null,industryValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists specialist");
        db.execSQL("drop table if exists tech");
        db.execSQL("drop table if exists industry");
        onCreate(db);
    }
}
