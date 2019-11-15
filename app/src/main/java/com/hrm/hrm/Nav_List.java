package com.hrm.hrm;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.hrm.hrm.Bean.AchievementsBean;
import com.hrm.hrm.Bean.DocBean;
import com.hrm.hrm.Bean.FamailyMembersBean;
import com.hrm.hrm.Bean.HobbiesBean;
import com.hrm.hrm.Bean.QualificationBean;
import com.hrm.hrm.Bean.SkillsBean;
import com.hrm.hrm.Bean.WorkingBean;
import com.hrm.hrm.utils.AppPreferences;
import com.hrm.hrm.utils.DyncamicListviewHeight;
import com.hrm.hrm.utils.Endpoints;
import com.hrm.hrm.utils.ZoomableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;

public class Nav_List extends AppCompatActivity {
    TextView tv_header;

    ArrayList<String> arrayListHeader;
    ProgressDialog pd1;
    Bundle bundle;
    String data;
    ScrollView sc_profile;
    LinearLayout ll_masterdetails, ll_personal, ll_passport, ll_Qualifications, ll_FamilyMembers, ll_WokingCompanies, ll_Documents,
            ll_Hobbies, ll_Achievements, ll_Skills, ll_medical, ll_back;
    String success, msg, email, userid, id, first_name, last_name, phone, user_image, current_latitude, current_longitude, user_type,
            EmpId, EmpCode, Name, Gender, DOB, DOJ, DOC,
            FatherName, SpouseName, Photo, PFApply, PFAppleDate, PFNo, PFUAN, ESIC_Application, ESIC_No,
            PAN, Aadhar, PTApplication, BranchName,
            Grade, Department, Designation, Division, CostCenter, Project, Category, PaymentMode,
            BankName, AccountNo, IfscCode, AccountName, DOL, PersonalDetail,
            PassportNo, PassportIssueDate, PassportExpiryDate, PassportPlaceOfIssue, VehicleDetails,
            DrivingLicenseNo, DrivingLicenseIssueDate,
            DrivingLicenseExpiryDate, TwoWheeler, FourWheeler, DrugLicenseNo, DrugIssueDate,
            DrugExpiryDate, Address1, Address2, City, State, Country,
            PinCode, Address1Per, Address2Per, CityPer, StatePer, CountryPer, PinCodePer, Telephone, MartialStatus,
            PersonalMobileNo, MarriageDate, OfficialMobileNo, NoOfChildren, Religion, EmailPersonal, BloodGroup, EmailOfficial,
            Height, ESICDispensary, EmergencyContactName, Spectacles, EmergencyContactNo,
            Qualifications, QualificationName, InstituteName, BoardName, MainSubjectName, DivisionName, MarksPercentage, PassingYear, Weightage,
            FamilyMembers, NameFamily, Relation, DateOfBirth, AddressFamily, PhoneNoFamily, EmailFamily, GartutiyNominee, PFNominee, PensionNominee, MedicalInsurance, MedicalInsNominee,
            WokingCompanies, EmployerDetail, Place, FromDate, ToDate, BasicSalary, TotalSalary, ReasonForLeaving, DesigScope, SupervisiorDetail, Currency, TotalExperience,
            Documents, DocumentName, Remarks, FilePath,
            Skills, SkillName, SkillRemark, Weight, Hobbies, HobbiName, HobbiRemark,
            Achievements, AchievementName, AchievementDate, AchievementRemark,
            MedicalPolicies;

    TabLayout tab_layout_loaction;
    String offerstatus = "0";
    String tabname;

    String DATE_FORMAT_I, DATE_FORMAT_O;


    ExpandableHeightListView lv_skills, lv_qualification, lv_famailymembers, lv_document, lv_hobbies, lv_Achievements, lv_working_cmpny;
    ArrayList<QualificationBean> qualificationBeen;
    ArrayList<FamailyMembersBean> famailyMembersBeen;
    ArrayList<SkillsBean> skillsBeen;
    ArrayList<DocBean> docBeen;
    ArrayList<HobbiesBean> hobbiesBeen;
    ArrayList<AchievementsBean> achievementsBeen;
    ArrayList<WorkingBean> workingBeen;


    SimpleDateFormat formatInput, formatOutput;
    Date datedob = null;
    Date datedoc = null;
    Date datedoj = null;
    Date datePFdate = null;
    Date dateDol = null;
    Date dateTo = null;
    Date dateFrom = null;


    TextView tv_name, tv_emp_code, tv_gender, tv_DOB, tv_age, tv_branch, tv_dept, tv_designation,
            tv_fathername, tv_spousename, tv_doc, tv_doj, tv_pf_applicable, tv_pf_date, tv_pf_no,
            tv_PFUAN, tv_ESIC_Application, tv_ESIC_no, tv_PAN, tv_addhar, tv_pt_applicable, tv_grade,
            tv_cost_Center, tv_project, tv_Category, tv_PaymentMode, tv_Bankname, tv_AccountNo, tv_IfscCode,
            tv_AccountName, tv_DOL, tv_passportno, tv_PassportIssueDate, tv_PassportExpiryDate, tv_PassportPlaceOfIssue,
            tv_VehicleDetails, tv_DrivingLicenseNo, tv_DrivingLicenseIssueDate, tv_DrivingLicenseExpiryDate, tv_TwoWheeler,
            tv_FourWheeler, tv_DrugLicenseNo, tv_DrugIssueDate, tv_DrugExpiryDate,
            tv_present_add, tv_permanent_add, tv_telephone, tv_personal_mobile, tv_Official_mobile, tv_email_personal,
            tv_email_official, tv_esic_dispensary, tv_emergency_name, tv_emergency_no, tv_marital_status, tv_Marriage_Date,
            tv_no_of_child, tv_Blood_Group, tv_Height, tv_Weight, tv_Spectacles;

    String profile = Endpoints.MYPROFILE;

    QualificationAdapter qualificationAdapter;
    FamailyAdapter famailyAdapter;
    SkillsAdapter skillsAdapter;
    DocAdapter docAdapter;
    HobbiesAdapter hobbiesAdapter;
    AchievementsAdapter achievementsAdapter;
    WorkingAdapter workingAdapter;
    TextView tv_no_data_qualification, tv_no_data_faimily, tv_no_data_working_cmpny, tv_no_data_Documents,
            tv_no_data_Hobbies, tv_no_data_Skills,tv_no_data_Achievements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav__list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));

        }

        lv_qualification = (ExpandableHeightListView) findViewById(R.id.lv_qualification);
        lv_famailymembers = (ExpandableHeightListView) findViewById(R.id.lv_famailymembers);
        lv_skills = (ExpandableHeightListView) findViewById(R.id.lv_skills);
        lv_document = (ExpandableHeightListView) findViewById(R.id.lv_document);
        lv_hobbies = (ExpandableHeightListView) findViewById(R.id.lv_hobbies);
        lv_Achievements = (ExpandableHeightListView) findViewById(R.id.lv_Achievements);
        lv_working_cmpny = (ExpandableHeightListView) findViewById(R.id.lv_working_cmpny);
//        DyncamicListviewHeight.setListViewHeightBasedOnChildren(lv_skills);


        tv_no_data_qualification = (TextView) findViewById(R.id.tv_no_data_qualification);
        tv_no_data_faimily = (TextView) findViewById(R.id.tv_no_data_faimily);
        tv_no_data_working_cmpny = (TextView) findViewById(R.id.tv_no_data_working_cmpny);
        tv_no_data_Documents = (TextView) findViewById(R.id.tv_no_data_Documents);
        tv_no_data_Hobbies = (TextView) findViewById(R.id.tv_no_data_Hobbies);
        tv_no_data_Skills = (TextView) findViewById(R.id.tv_no_data_Skills);
        tv_no_data_Achievements = (TextView) findViewById(R.id.tv_no_data_Achievements);

        qualificationBeen = new ArrayList<>();
        famailyMembersBeen = new ArrayList<>();
        skillsBeen = new ArrayList<>();
        docBeen = new ArrayList<>();
        hobbiesBeen = new ArrayList<>();
        achievementsBeen = new ArrayList<>();
        workingBeen = new ArrayList<>();


        qualificationAdapter = new QualificationAdapter(Nav_List.this, qualificationBeen);
        lv_qualification.setAdapter(qualificationAdapter);
        lv_qualification.setExpanded(true);
        qualificationAdapter.notifyDataSetChanged();

        famailyAdapter = new FamailyAdapter(Nav_List.this, famailyMembersBeen);
        lv_famailymembers.setAdapter(famailyAdapter);
        lv_famailymembers.setExpanded(true);
        famailyAdapter.notifyDataSetChanged();

        skillsAdapter = new SkillsAdapter(Nav_List.this, skillsBeen);
        lv_skills.setAdapter(skillsAdapter);
        lv_skills.setExpanded(true);
        skillsAdapter.notifyDataSetChanged();


        docAdapter = new DocAdapter(Nav_List.this, docBeen);
        lv_document.setAdapter(docAdapter);
        lv_document.setExpanded(true);
        docAdapter.notifyDataSetChanged();

        hobbiesAdapter = new HobbiesAdapter(Nav_List.this, hobbiesBeen);
        lv_hobbies.setAdapter(hobbiesAdapter);
        lv_hobbies.setExpanded(true);
        hobbiesAdapter.notifyDataSetChanged();


        achievementsAdapter = new AchievementsAdapter(Nav_List.this, achievementsBeen);
        lv_Achievements.setAdapter(achievementsAdapter);
        lv_Achievements.setExpanded(true);
        achievementsAdapter.notifyDataSetChanged();

        workingAdapter = new WorkingAdapter(Nav_List.this, workingBeen);
        lv_working_cmpny.setAdapter(workingAdapter);
        lv_working_cmpny.setExpanded(true);


        bundle = getIntent().getExtras();
        data = bundle.getString("Menu");
        tv_header = (TextView) findViewById(R.id.tv_header);
        tv_header.setText(data);

        arrayListHeader = new ArrayList<String>();
        tab_layout_loaction = (TabLayout) findViewById(R.id.tab_layout_loaction);
        sc_profile = (ScrollView) findViewById(R.id.sc_profile);

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_emp_code = (TextView) findViewById(R.id.tv_emp_code);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_DOB = (TextView) findViewById(R.id.tv_DOB);
        tv_fathername = (TextView) findViewById(R.id.tv_fathername);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_branch = (TextView) findViewById(R.id.tv_branch);
        tv_dept = (TextView) findViewById(R.id.tv_dept);
        tv_designation = (TextView) findViewById(R.id.tv_designation);
        tv_spousename = (TextView) findViewById(R.id.tv_spousename);
        tv_doc = (TextView) findViewById(R.id.tv_doc);
        tv_doj = (TextView) findViewById(R.id.tv_doj);
        tv_pf_applicable = (TextView) findViewById(R.id.tv_pf_applicable);
        tv_pf_date = (TextView) findViewById(R.id.tv_pf_date);
        tv_pf_no = (TextView) findViewById(R.id.tv_pf_no);
        tv_PFUAN = (TextView) findViewById(R.id.tv_PFUAN);
        tv_ESIC_Application = (TextView) findViewById(R.id.tv_ESIC_Application);
        tv_ESIC_no = (TextView) findViewById(R.id.tv_ESIC_no);
        tv_PAN = (TextView) findViewById(R.id.tv_PAN);
        tv_addhar = (TextView) findViewById(R.id.tv_addhar);
        tv_pt_applicable = (TextView) findViewById(R.id.tv_pt_applicable);
        tv_grade = (TextView) findViewById(R.id.tv_grade);
        tv_cost_Center = (TextView) findViewById(R.id.tv_cost_Center);
        tv_project = (TextView) findViewById(R.id.tv_project);
        tv_Category = (TextView) findViewById(R.id.tv_Category);
        tv_PaymentMode = (TextView) findViewById(R.id.tv_PaymentMode);
        tv_Bankname = (TextView) findViewById(R.id.tv_Bankname);
        tv_AccountNo = (TextView) findViewById(R.id.tv_AccountNo);
        tv_IfscCode = (TextView) findViewById(R.id.tv_IfscCode);
        tv_AccountName = (TextView) findViewById(R.id.tv_AccountName);
        tv_DOL = (TextView) findViewById(R.id.tv_DOL);

        tv_present_add = (TextView) findViewById(R.id.tv_present_add);
        tv_permanent_add = (TextView) findViewById(R.id.tv_permanent_add);
        tv_telephone = (TextView) findViewById(R.id.tv_telephone);
        tv_personal_mobile = (TextView) findViewById(R.id.tv_personal_mobile);
        tv_Official_mobile = (TextView) findViewById(R.id.tv_Official_mobile);
        tv_email_personal = (TextView) findViewById(R.id.tv_email_personal);
        tv_email_official = (TextView) findViewById(R.id.tv_email_official);
        tv_esic_dispensary = (TextView) findViewById(R.id.tv_esic_dispensary);
        tv_emergency_name = (TextView) findViewById(R.id.tv_emergency_name);
        tv_emergency_no = (TextView) findViewById(R.id.tv_emergency_no);
        tv_marital_status = (TextView) findViewById(R.id.tv_marital_status);
        tv_Marriage_Date = (TextView) findViewById(R.id.tv_Marriage_Date);
        tv_no_of_child = (TextView) findViewById(R.id.tv_no_of_child);
        tv_Blood_Group = (TextView) findViewById(R.id.tv_Blood_Group);
        tv_Height = (TextView) findViewById(R.id.tv_Height);
        tv_Weight = (TextView) findViewById(R.id.tv_Weight);
        tv_Spectacles = (TextView) findViewById(R.id.tv_Spectacles);


        tv_passportno = (TextView) findViewById(R.id.tv_passportno);
        tv_PassportIssueDate = (TextView) findViewById(R.id.tv_PassportIssueDate);
        tv_PassportExpiryDate = (TextView) findViewById(R.id.tv_PassportExpiryDate);
        tv_PassportPlaceOfIssue = (TextView) findViewById(R.id.tv_PassportPlaceOfIssue);
        tv_VehicleDetails = (TextView) findViewById(R.id.tv_VehicleDetails);
        tv_DrivingLicenseNo = (TextView) findViewById(R.id.tv_DrivingLicenseNo);
        tv_DrivingLicenseIssueDate = (TextView) findViewById(R.id.tv_DrivingLicenseIssueDate);
        tv_DrivingLicenseExpiryDate = (TextView) findViewById(R.id.tv_DrivingLicenseExpiryDate);
        tv_TwoWheeler = (TextView) findViewById(R.id.tv_TwoWheeler);
        tv_FourWheeler = (TextView) findViewById(R.id.tv_FourWheeler);
        tv_DrugLicenseNo = (TextView) findViewById(R.id.tv_DrugLicenseNo);
        tv_DrugIssueDate = (TextView) findViewById(R.id.tv_DrugIssueDate);
        tv_DrugExpiryDate = (TextView) findViewById(R.id.tv_DrugExpiryDate);


        DATE_FORMAT_I = "yyyy-MM-dd'T'HH:mm:ss";
        DATE_FORMAT_O = "yyyy-MM-dd";


        formatInput = new SimpleDateFormat(DATE_FORMAT_I);
        formatOutput = new SimpleDateFormat(DATE_FORMAT_O);

        ll_masterdetails = (LinearLayout) findViewById(R.id.ll_masterdetails);
        ll_personal = (LinearLayout) findViewById(R.id.ll_personal);
        ll_passport = (LinearLayout) findViewById(R.id.ll_passport);
        ll_Qualifications = (LinearLayout) findViewById(R.id.ll_Qualifications);
        ll_FamilyMembers = (LinearLayout) findViewById(R.id.ll_FamilyMembers);
        ll_WokingCompanies = (LinearLayout) findViewById(R.id.ll_WokingCompanies);
        ll_Documents = (LinearLayout) findViewById(R.id.ll_Documents);
        ll_Hobbies = (LinearLayout) findViewById(R.id.ll_Hobbies);
        ll_Achievements = (LinearLayout) findViewById(R.id.ll_Achievements);
        ll_Skills = (LinearLayout) findViewById(R.id.ll_Skills);
        ll_medical = (LinearLayout) findViewById(R.id.ll_medical);


        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.trans_left_in,
                        R.anim.trans_left_out);
            }
        });
       String Cid = AppPreferences.loadPreferences(Nav_List.this, "CompanyId");
        String CompanyCode= AppPreferences.loadPreferences(Nav_List.this, "CompanyCode");

        Endpoints endpoints=new Endpoints(CompanyCode);

        getProfile();

    }

    private void getProfile() {
        pd1 = new ProgressDialog(Nav_List.this);
        pd1.setMessage("Loading Please Wait...");
        pd1.setCanceledOnTouchOutside(false);
        pd1.show();


//        String url = "http://rajapi.7stepstechnologies.co.in/EmployeeLogin";

            /*    {
                    "EmployeeId":"1",
                        "CompanyId":"ns",
                        "UserName":"101",
                        "Password":"101",
                        "DeviceId":"12345"
                }*/
        RequestQueue sr2 = Volley.newRequestQueue(Nav_List.this);

        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("CompanyId", AppPreferences.loadPreferences(Nav_List.this, "CompanyId"));
        jsonParams.put("EmployeeId", AppPreferences.loadPreferences(Nav_List.this, "EmployeeId"));
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(Nav_List.this, "CompanyCode"));

        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.POST, profile, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //  System.out.println("====response===" + response);
                        pd1.dismiss();
                        qualificationBeen.clear();
                        famailyMembersBeen.clear();
                        try {
                            pd1.dismiss();
                            sc_profile.setVisibility(View.VISIBLE);
                            ll_masterdetails.setVisibility(View.VISIBLE);
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");


                            if (result.equalsIgnoreCase("true")) {
                                JSONObject data = response.getJSONObject("data");

                                EmpId = data.getString("EmpId");
                                EmpCode = data.getString("EmpCode");
                                Name = data.getString("Name");
                                Gender = data.getString("Gender");
                                DOB = data.getString("DOB");
                                DOJ = data.getString("DOJ");
                                DOC = data.getString("DOC");
                                FatherName = data.getString("FatherName");
                                SpouseName = data.getString("SpouseName");
                                Photo = data.getString("Photo");
                                PFApply = data.getString("PFApply");
                                PFAppleDate = data.getString("PFAppleDate");
                                PFNo = data.getString("PFNo");
                                PFUAN = data.getString("PFUAN");
                                ESIC_Application = data.getString("ESIC_Application");
                                ESIC_No = data.getString("ESIC_No");
                                PAN = data.getString("PAN");
                                Aadhar = data.getString("Aadhar");
                                PTApplication = data.getString("PTApplication");
                                BranchName = data.getString("BranchName");
                                Grade = data.getString("Grade");
                                Department = data.getString("Department");
                                Designation = data.getString("Designation");
                                Division = data.getString("Division");
                                CostCenter = data.getString("CostCenter");
                                Project = data.getString("Project");
                                Category = data.getString("Category");
                                PaymentMode = data.getString("PaymentMode");
                                BankName = data.getString("BankName");
                                AccountNo = data.getString("AccountNo");
                                IfscCode = data.getString("IfscCode");
                                AccountName = data.getString("AccountName");
                                DOL = data.getString("DOL");
//                                PersonalDetail = data.getString("PersonalDetail");
                                JSONObject PersonalDetail = data.getJSONObject("PersonalDetail");
                                Address1 = PersonalDetail.getString("Address1");
                                Address2 = PersonalDetail.getString("Address2");
                                City = PersonalDetail.getString("City");
                                State = PersonalDetail.getString("State");
                                Country = PersonalDetail.getString("Country");
                                PinCode = PersonalDetail.getString("PinCode");
                                Address1Per = PersonalDetail.getString("Address1Per");
                                Address2Per = PersonalDetail.getString("Address2Per");
                                CityPer = PersonalDetail.getString("CityPer");
                                StatePer = PersonalDetail.getString("StatePer");
                                CountryPer = PersonalDetail.getString("CountryPer");
                                PinCodePer = PersonalDetail.getString("PinCodePer");
                                Telephone = PersonalDetail.getString("Telephone");
                                MartialStatus = PersonalDetail.getString("MartialStatus");
                                PersonalMobileNo = PersonalDetail.getString("PersonalMobileNo");
                                MarriageDate = PersonalDetail.getString("MarriageDate");
                                OfficialMobileNo = PersonalDetail.getString("OfficialMobileNo");
                                NoOfChildren = PersonalDetail.getString("NoOfChildren");
                                Religion = PersonalDetail.getString("Religion");
                                EmailPersonal = PersonalDetail.getString("EmailPersonal");
                                BloodGroup = PersonalDetail.getString("BloodGroup");
                                EmailOfficial = PersonalDetail.getString("EmailOfficial");
                                Height = PersonalDetail.getString("Height");
                                Weight = PersonalDetail.getString("Weight");
                                ESICDispensary = PersonalDetail.getString("ESICDispensary");
                                EmergencyContactName = PersonalDetail.getString("EmergencyContactName");
                                Spectacles = PersonalDetail.getString("Spectacles");
                                EmergencyContactNo = PersonalDetail.getString("EmergencyContactNo");


                                JSONObject Passport = data.getJSONObject("Passport");
                                PassportNo = Passport.getString("PassportNo");
                                PassportIssueDate = Passport.getString("PassportIssueDate");
                                PassportExpiryDate = Passport.getString("PassportExpiryDate");
                                PassportPlaceOfIssue = Passport.getString("PassportPlaceOfIssue");
                                VehicleDetails = Passport.getString("VehicleDetails");
                                DrivingLicenseNo = Passport.getString("DrivingLicenseNo");
                                DrivingLicenseIssueDate = Passport.getString("DrivingLicenseIssueDate");
                                DrivingLicenseExpiryDate = Passport.getString("DrivingLicenseExpiryDate");
                                TwoWheeler = Passport.getString("TwoWheeler");
                                FourWheeler = Passport.getString("FourWheeler");
                                DrugLicenseNo = Passport.getString("DrugLicenseNo");
                                DrugIssueDate = Passport.getString("DrugIssueDate");
                                DrugExpiryDate = Passport.getString("DrugExpiryDate");


                                JSONArray QualificationsArray = data.getJSONArray("Qualifications");
                                if (QualificationsArray.length() == 0) {

                                    lv_qualification.setVisibility(View.GONE);
                                    tv_no_data_qualification.setVisibility(View.VISIBLE);

                                } else {
                                    lv_qualification.setVisibility(View.VISIBLE);
                                    tv_no_data_qualification.setVisibility(View.GONE);

                                    for (int i = 0; i < QualificationsArray.length(); i++) {

                                        JSONObject QualiFicationObject = QualificationsArray.getJSONObject(i);
                                        QualificationName = QualiFicationObject.getString("QualificationName");
                                        InstituteName = QualiFicationObject.getString("InstituteName");
                                        BoardName = QualiFicationObject.getString("BoardName");
                                        MainSubjectName = QualiFicationObject.getString("MainSubjectName");
                                        DivisionName = QualiFicationObject.getString("DivisionName");
                                        MarksPercentage = QualiFicationObject.getString("MarksPercentage");
                                        PassingYear = QualiFicationObject.getString("PassingYear");
                                        Weightage = QualiFicationObject.getString("Weightage");

                                        qualificationBeen.add(new QualificationBean(QualificationName, InstituteName, BoardName, MainSubjectName, DivisionName,
                                                MarksPercentage, PassingYear, Weightage));


                                    }
                                    qualificationAdapter.notifyDataSetChanged();


                                }


                                JSONArray FamilyMembersJsonArray = data.getJSONArray("FamilyMembers");
                                if (FamilyMembersJsonArray.length() == 0) {
                                    lv_famailymembers.setVisibility(View.GONE);
                                    tv_no_data_faimily.setVisibility(View.VISIBLE);

                                } else {
                                    lv_famailymembers.setVisibility(View.VISIBLE);
                                    tv_no_data_faimily.setVisibility(View.GONE);
                                    for (int k = 0; k < FamilyMembersJsonArray.length(); k++) {
                                        JSONObject FamilyMembersJsonObject = FamilyMembersJsonArray.getJSONObject(k);
                                        NameFamily = FamilyMembersJsonObject.getString("NameFamily");
                                        Relation = FamilyMembersJsonObject.getString("Relation");
                                        DateOfBirth = FamilyMembersJsonObject.getString("DateOfBirth");
                                        AddressFamily = FamilyMembersJsonObject.getString("AddressFamily");
                                        PhoneNoFamily = FamilyMembersJsonObject.getString("PhoneNoFamily");
                                        EmailFamily = FamilyMembersJsonObject.getString("EmailFamily");
                                        GartutiyNominee = FamilyMembersJsonObject.getString("GartutiyNominee");
                                        PFNominee = FamilyMembersJsonObject.getString("PFNominee");
                                        PensionNominee = FamilyMembersJsonObject.getString("PensionNominee");
                                        MedicalInsurance = FamilyMembersJsonObject.getString("MedicalInsurance");
                                        MedicalInsNominee = FamilyMembersJsonObject.getString("MedicalInsNominee");

                                        famailyMembersBeen.add(new FamailyMembersBean(NameFamily, Relation, DateOfBirth, AddressFamily, PhoneNoFamily, EmailFamily, GartutiyNominee,
                                                PFNominee, PensionNominee, MedicalInsurance, MedicalInsNominee));
                                    }


                                    famailyAdapter.notifyDataSetChanged();

                                }


                                JSONArray WokingCompaniesJsonArray = data.getJSONArray("WokingCompanies");

                                if (WokingCompaniesJsonArray.length() == 0) {
                                    lv_working_cmpny.setVisibility(View.GONE);
                                    tv_no_data_working_cmpny.setVisibility(View.VISIBLE);
                                } else {
                                    lv_working_cmpny.setVisibility(View.VISIBLE);
                                    tv_no_data_working_cmpny.setVisibility(View.GONE);
                                    for (int l = 0; l < WokingCompaniesJsonArray.length(); l++) {
                                        JSONObject WokingCompaniesJsonObject = WokingCompaniesJsonArray.getJSONObject(l);


                                        EmployerDetail = WokingCompaniesJsonObject.getString("EmployerDetail");
                                        Place = WokingCompaniesJsonObject.getString("Place");
                                        FromDate = WokingCompaniesJsonObject.getString("FromDate");
                                        ToDate = WokingCompaniesJsonObject.getString("ToDate");
                                        BasicSalary = WokingCompaniesJsonObject.getString("BasicSalary");
                                        TotalSalary = WokingCompaniesJsonObject.getString("TotalSalary");
                                        ReasonForLeaving = WokingCompaniesJsonObject.getString("ReasonForLeaving");
                                        DesigScope = WokingCompaniesJsonObject.getString("DesigScope");
                                        SupervisiorDetail = WokingCompaniesJsonObject.getString("SupervisiorDetail");
                                        Currency = WokingCompaniesJsonObject.getString("Currency");
                                        TotalExperience = WokingCompaniesJsonObject.getString("TotalExperience");


                                        workingBeen.add(new WorkingBean(EmployerDetail, Place, FromDate, ToDate, BasicSalary, TotalSalary, ReasonForLeaving, DesigScope
                                                , SupervisiorDetail, Currency, TotalExperience));


                                    }
                                    workingAdapter.notifyDataSetChanged();

                                }

                                JSONArray DocumentsJsonArray = data.getJSONArray("Documents");
                                if (DocumentsJsonArray.length() == 0) {
                                    tv_no_data_Documents.setVisibility(View.VISIBLE);
                                    lv_document.setVisibility(View.GONE);

                                } else {
                                    tv_no_data_Documents.setVisibility(View.GONE);
                                    lv_document.setVisibility(View.VISIBLE);
                                    for (int m = 0; m < DocumentsJsonArray.length(); m++) {
                                        JSONObject DocumanetsJsonObject = DocumentsJsonArray.getJSONObject(m);

                                        DocumentName = DocumanetsJsonObject.getString("DocumentName");
                                        Remarks = DocumanetsJsonObject.getString("Remarks");
                                        FilePath = DocumanetsJsonObject.getString("FilePath");
                                        docBeen.add(new DocBean(DocumentName, Remarks, FilePath));

                                    }
                                    docAdapter.notifyDataSetChanged();

                                }


                                JSONArray SkillsJsonArray = data.getJSONArray("Skills");
                                if (SkillsJsonArray.length() == 0) {
                                    lv_skills.setVisibility(View.GONE);
                                    tv_no_data_Skills.setVisibility(View.VISIBLE);
                                } else {
                                    lv_skills.setVisibility(View.VISIBLE);
                                    tv_no_data_Skills.setVisibility(View.GONE);
                                    for (int n = 0; n < SkillsJsonArray.length(); n++) {
                                        JSONObject SkillsJsonObject = SkillsJsonArray.getJSONObject(n);

                                        SkillName = SkillsJsonObject.getString("SkillName");
                                        SkillRemark = SkillsJsonObject.getString("SkillRemark");
                                        skillsBeen.add(new SkillsBean(SkillName, SkillRemark));

                                    }

                                    skillsAdapter.notifyDataSetChanged();

                                }


                                JSONArray HobbiesJsonArray = data.getJSONArray("Hobbies");
                                if (HobbiesJsonArray.length()==0){
                                    tv_no_data_Hobbies.setVisibility(View.VISIBLE);
                                    lv_hobbies.setVisibility(View.GONE);

                                }else {
                                    tv_no_data_Hobbies.setVisibility(View.GONE);
                                    lv_hobbies.setVisibility(View.VISIBLE);
                                    for (int o = 0; o < HobbiesJsonArray.length(); o++) {
                                        JSONObject HobbiesJsonObject = HobbiesJsonArray.getJSONObject(o);

                                        HobbiName = HobbiesJsonObject.getString("HobbiName");
                                        HobbiRemark = HobbiesJsonObject.getString("HobbiRemark");
                                        hobbiesBeen.add(new HobbiesBean(HobbiName, HobbiRemark));

                                    }
                                    hobbiesAdapter.notifyDataSetChanged();
                                }

                                JSONArray AchievementsJsonArray = data.getJSONArray("Achievements");
                               if (AchievementsJsonArray.length()==0){
                                   tv_no_data_Achievements.setVisibility(View.VISIBLE);
                                   lv_Achievements.setVisibility(View.GONE);

                               }else {
                                   tv_no_data_Achievements.setVisibility(View.GONE);
                                   lv_Achievements.setVisibility(View.VISIBLE);
                                   for (int p = 0; p < AchievementsJsonArray.length(); p++) {
                                       JSONObject AchievementsJsonObject = AchievementsJsonArray.getJSONObject(p);

                                       AchievementName = AchievementsJsonObject.getString("AchievementName");
                                       AchievementDate = AchievementsJsonObject.getString("AchievementDate");
                                       AchievementRemark = AchievementsJsonObject.getString("AchievementRemark");
                                       achievementsBeen.add(new AchievementsBean(AchievementName, AchievementDate, AchievementRemark));

                                   }

                               }

                                JSONArray MedicalPoliciesJsonArray = data.getJSONArray("MedicalPolicies");
//                                AppPreferences.savePreferences(MyProfile.this, "id", userid);
//                                AppPreferences.savePreferences(MyProfile.this, "firstname", first_name);
//                                AppPreferences.savePreferences(MyProfile.this, "last_name", last_name);
//                                AppPreferences.savePreferences(MyProfile.this, "phone", phone);
//                                AppPreferences.savePreferences(MyProfile.this, "user_image", user_image);
//                                AppPreferences.savePreferences(MyProfile.this, "current_latitude", current_latitude);
//                                AppPreferences.savePreferences(MyProfile.this, "current_longitude", current_longitude);
//                                AppPreferences.savePreferences(MyProfile.this, "user_type", user_type);

                                arrayListHeader.add("Master Details");
                                arrayListHeader.add("Personal Details");
                                arrayListHeader.add("Passport Details");
                                arrayListHeader.add("Qualification");
                                arrayListHeader.add("Family Members");
                                arrayListHeader.add("Work Experience");
                                arrayListHeader.add("Document Attachment");
                                arrayListHeader.add("Hobbies");
                                arrayListHeader.add("Achievements");
                                arrayListHeader.add("Skills");
                                arrayListHeader.add("Medical Insurance");


                                for (int j = 0; j < arrayListHeader.size(); j++) {
                                    System.out.println(arrayListHeader.get(j)); //prints element i

                                    //Adding the tabs using addTab() method
                                    tab_layout_loaction.addTab(tab_layout_loaction.newTab().setText(arrayListHeader.get(j)));
                                    tab_layout_loaction.setTabGravity(TabLayout.GRAVITY_FILL);

                                }

                                tab_layout_loaction.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                    @Override
                                    public void onTabSelected(TabLayout.Tab tab) {

                                        // Toast.makeText(BusinessList.this, ">>" + tab.getText(), Toast.LENGTH_SHORT).show();
                                        //    viewPager.setCurrentItem(tab.getPosition());


                                        tabname = tab.getText().toString();
                                        Log.e("LocalityData2", " " + tabname);

                                        if (tabname.equalsIgnoreCase("Master Details")) {

                                            ll_masterdetails.setVisibility(View.VISIBLE);
                                            ll_personal.setVisibility(View.GONE);
                                            ll_passport.setVisibility(View.GONE);
                                            ll_Qualifications.setVisibility(View.GONE);
                                            ll_FamilyMembers.setVisibility(View.GONE);
                                            ll_WokingCompanies.setVisibility(View.GONE);
                                            ll_Documents.setVisibility(View.GONE);
                                            ll_Hobbies.setVisibility(View.GONE);
                                            ll_Achievements.setVisibility(View.GONE);
                                            ll_Skills.setVisibility(View.GONE);
                                            ll_medical.setVisibility(View.GONE);

                                        } else if (tabname.equalsIgnoreCase("Personal Details")) {

                                            ll_masterdetails.setVisibility(View.GONE);
                                            ll_personal.setVisibility(View.VISIBLE);
                                            ll_passport.setVisibility(View.GONE);
                                            ll_Qualifications.setVisibility(View.GONE);
                                            ll_FamilyMembers.setVisibility(View.GONE);
                                            ll_WokingCompanies.setVisibility(View.GONE);
                                            ll_Documents.setVisibility(View.GONE);
                                            ll_Hobbies.setVisibility(View.GONE);
                                            ll_Achievements.setVisibility(View.GONE);
                                            ll_Skills.setVisibility(View.GONE);
                                            ll_medical.setVisibility(View.GONE);

                                        } else if (tabname.equalsIgnoreCase("Passport Details")) {

                                            ll_masterdetails.setVisibility(View.GONE);
                                            ll_personal.setVisibility(View.GONE);
                                            ll_passport.setVisibility(View.VISIBLE);
                                            ll_Qualifications.setVisibility(View.GONE);
                                            ll_FamilyMembers.setVisibility(View.GONE);
                                            ll_WokingCompanies.setVisibility(View.GONE);
                                            ll_Documents.setVisibility(View.GONE);
                                            ll_Hobbies.setVisibility(View.GONE);
                                            ll_Achievements.setVisibility(View.GONE);
                                            ll_Skills.setVisibility(View.GONE);
                                            ll_medical.setVisibility(View.GONE);

                                        } else if (tabname.equalsIgnoreCase("Qualification")) {

                                            ll_masterdetails.setVisibility(View.GONE);
                                            ll_personal.setVisibility(View.GONE);
                                            ll_passport.setVisibility(View.GONE);
                                            ll_Qualifications.setVisibility(View.VISIBLE);
                                            ll_FamilyMembers.setVisibility(View.GONE);
                                            ll_WokingCompanies.setVisibility(View.GONE);
                                            ll_Documents.setVisibility(View.GONE);
                                            ll_Hobbies.setVisibility(View.GONE);
                                            ll_Achievements.setVisibility(View.GONE);
                                            ll_Skills.setVisibility(View.GONE);
                                            ll_medical.setVisibility(View.GONE);

                                        } else if (tabname.equalsIgnoreCase("Family Members")) {

                                            ll_masterdetails.setVisibility(View.GONE);
                                            ll_personal.setVisibility(View.GONE);
                                            ll_passport.setVisibility(View.GONE);
                                            ll_Qualifications.setVisibility(View.GONE);
                                            ll_FamilyMembers.setVisibility(View.VISIBLE);
                                            ll_WokingCompanies.setVisibility(View.GONE);
                                            ll_Documents.setVisibility(View.GONE);
                                            ll_Hobbies.setVisibility(View.GONE);
                                            ll_Achievements.setVisibility(View.GONE);
                                            ll_Skills.setVisibility(View.GONE);
                                            ll_medical.setVisibility(View.GONE);


                                        } else if (tabname.equalsIgnoreCase("Work Experience")) {

                                            ll_masterdetails.setVisibility(View.GONE);
                                            ll_personal.setVisibility(View.GONE);
                                            ll_passport.setVisibility(View.GONE);
                                            ll_Qualifications.setVisibility(View.GONE);
                                            ll_FamilyMembers.setVisibility(View.GONE);
                                            ll_WokingCompanies.setVisibility(View.VISIBLE);
                                            ll_Documents.setVisibility(View.GONE);
                                            ll_Hobbies.setVisibility(View.GONE);
                                            ll_Achievements.setVisibility(View.GONE);
                                            ll_Skills.setVisibility(View.GONE);


                                        } else if (tabname.equalsIgnoreCase("Document Attachment")) {

                                            ll_masterdetails.setVisibility(View.GONE);
                                            ll_personal.setVisibility(View.GONE);
                                            ll_passport.setVisibility(View.GONE);
                                            ll_Qualifications.setVisibility(View.GONE);
                                            ll_FamilyMembers.setVisibility(View.GONE);
                                            ll_WokingCompanies.setVisibility(View.GONE);
                                            ll_Documents.setVisibility(View.VISIBLE);
                                            ll_Hobbies.setVisibility(View.GONE);
                                            ll_Achievements.setVisibility(View.GONE);
                                            ll_Skills.setVisibility(View.GONE);
                                            ll_medical.setVisibility(View.GONE);


                                        } else if (tabname.equalsIgnoreCase("Hobbies")) {


//                                            //
//                                            arrayListHeader.add("Achievements");
//                                            arrayListHeader.add("Skills");
                                            ll_masterdetails.setVisibility(View.GONE);
                                            ll_personal.setVisibility(View.GONE);
                                            ll_passport.setVisibility(View.GONE);
                                            ll_Qualifications.setVisibility(View.GONE);
                                            ll_FamilyMembers.setVisibility(View.GONE);
                                            ll_WokingCompanies.setVisibility(View.GONE);
                                            ll_Documents.setVisibility(View.GONE);
                                            ll_Hobbies.setVisibility(View.VISIBLE);
                                            ll_Achievements.setVisibility(View.GONE);
                                            ll_Skills.setVisibility(View.GONE);
                                            ll_medical.setVisibility(View.GONE);


                                        } else if (tabname.equalsIgnoreCase("Achievements")) {


//                                            //
//                                            arrayListHeader.add("Achievements");
//                                            arrayListHeader.add("Skills");
                                            ll_masterdetails.setVisibility(View.GONE);
                                            ll_personal.setVisibility(View.GONE);
                                            ll_passport.setVisibility(View.GONE);
                                            ll_Qualifications.setVisibility(View.GONE);
                                            ll_FamilyMembers.setVisibility(View.GONE);
                                            ll_WokingCompanies.setVisibility(View.GONE);
                                            ll_Documents.setVisibility(View.GONE);
                                            ll_Hobbies.setVisibility(View.GONE);
                                            ll_Achievements.setVisibility(View.VISIBLE);
                                            ll_Skills.setVisibility(View.GONE);
                                            ll_medical.setVisibility(View.GONE);


                                        } else if (tabname.equalsIgnoreCase("Skills")) {


//                                            //
//                                            arrayListHeader.add("Achievements");
//                                            arrayListHeader.add("Skills");
                                            ll_masterdetails.setVisibility(View.GONE);
                                            ll_personal.setVisibility(View.GONE);
                                            ll_passport.setVisibility(View.GONE);
                                            ll_Qualifications.setVisibility(View.GONE);
                                            ll_FamilyMembers.setVisibility(View.GONE);
                                            ll_WokingCompanies.setVisibility(View.GONE);
                                            ll_Documents.setVisibility(View.GONE);
                                            ll_Hobbies.setVisibility(View.GONE);
                                            ll_Achievements.setVisibility(View.GONE);
                                            ll_Skills.setVisibility(View.VISIBLE);
                                            ll_medical.setVisibility(View.GONE);


                                        } else if (tabname.equalsIgnoreCase("Medical Insurance")) {

                                            ll_masterdetails.setVisibility(View.GONE);
                                            ll_personal.setVisibility(View.GONE);
                                            ll_passport.setVisibility(View.GONE);
                                            ll_Qualifications.setVisibility(View.GONE);
                                            ll_FamilyMembers.setVisibility(View.GONE);
                                            ll_WokingCompanies.setVisibility(View.GONE);
                                            ll_Documents.setVisibility(View.GONE);
                                            ll_Hobbies.setVisibility(View.GONE);
                                            ll_Achievements.setVisibility(View.GONE);
                                            ll_Skills.setVisibility(View.GONE);
                                            ll_medical.setVisibility(View.VISIBLE);

                                        }
                                        //  Toast.makeText(BusinessList.this, "new data" + name + " " + id, Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onTabUnselected(TabLayout.Tab tab) {

                                    }

                                    @Override
                                    public void onTabReselected(TabLayout.Tab tab) {

                                    }
                                });


                                tv_name.setText(Name);
                                tv_emp_code.setText(EmpCode);
                                tv_gender.setText( Gender);

                                tv_fathername.setText(FatherName);
                                tv_spousename.setText(SpouseName);


                                tv_pf_applicable.setText("Yes");

                                tv_pf_no.setText(PFNo);
                                tv_PFUAN.setText(PFUAN);
                                tv_ESIC_Application.setText("Yes");
                                tv_ESIC_no.setText(ESIC_No);
                                tv_PAN.setText(PAN);
                                tv_addhar.setText(Aadhar);
                                tv_pt_applicable.setText("No");
                                tv_grade.setText(Grade);
                                tv_cost_Center.setText(CostCenter);
                                tv_project.setText(Project);
                                tv_Category.setText(Category);
                                tv_PaymentMode.setText(PaymentMode);
                                tv_Bankname.setText(BankName);
                                tv_AccountNo.setText(AccountNo);
                                tv_IfscCode.setText(IfscCode);
                                tv_AccountName.setText(AccountName);

                                tv_permanent_add.setText(Address1Per + "," + Address2Per + "," + CityPer + "," + StatePer + "," + CountryPer + "," + PinCodePer);
                                tv_present_add.setText(Address1 + "," + Address2 + "," + City + "," + State + "," + Country + "," + PinCode);
                                tv_telephone.setText(Telephone);
                                tv_personal_mobile.setText(PersonalMobileNo);
                                tv_Official_mobile.setText(OfficialMobileNo);
                                tv_email_personal.setText(EmailPersonal);
                                tv_email_official.setText(EmailOfficial);
                                tv_esic_dispensary.setText(ESICDispensary);
                                tv_emergency_name.setText(EmergencyContactName);
                                tv_emergency_no.setText(EmergencyContactNo);
                                tv_marital_status.setText(MartialStatus);
                                tv_Marriage_Date.setText(MarriageDate);
                                tv_no_of_child.setText(NoOfChildren);
                                tv_Blood_Group.setText(BloodGroup);
                                tv_Height.setText(Height);
                                tv_Weight.setText(Weight);
                                tv_Spectacles.setText(Spectacles);


                                tv_passportno.setText(PassportNo);
                                tv_PassportIssueDate.setText(PassportIssueDate);
                                tv_PassportExpiryDate.setText(PassportExpiryDate);
                                tv_PassportPlaceOfIssue.setText(PassportPlaceOfIssue);
                                tv_VehicleDetails.setText(VehicleDetails);
                                tv_DrivingLicenseNo.setText(DrivingLicenseNo);
                                tv_DrivingLicenseIssueDate.setText(DrivingLicenseIssueDate);
                                tv_DrivingLicenseExpiryDate.setText(DrivingLicenseExpiryDate);
                                tv_TwoWheeler.setText("Yes");
                                tv_FourWheeler.setText("Yes");
                                tv_DrugLicenseNo.setText(DrugLicenseNo);
                                tv_DrugIssueDate.setText(DrugIssueDate);
                                tv_DrugExpiryDate.setText(DrugExpiryDate);


                                try {
                                    datedob = formatInput.parse(DOB);
                                    datedoc = formatInput.parse(DOC);
                                    datedoj = formatInput.parse(DOJ);
                                    datePFdate = formatInput.parse(PFAppleDate);
                                    dateDol = formatInput.parse(DOL);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String dateStringDOB = formatOutput.format(datedob);
                                String dateStringDOC = formatOutput.format(datedoc);
                                String dateStringDOJ = formatOutput.format(datedoj);
                                String dateStringPFDate = formatOutput.format(datePFdate);
                                String dateStringDOL = formatOutput.format(dateDol);
                                tv_DOB.setText(dateStringDOB);
                                tv_doc.setText(dateStringDOC);
                                tv_doj.setText(dateStringDOJ);

                                tv_pf_date.setText(dateStringPFDate);
                                tv_DOL.setText(dateStringDOL);
                                String[] separated = dateStringDOB.split("-");
                                int year = Integer.parseInt(separated[0]);
                                int month = Integer.parseInt(separated[1]);
                                int day = Integer.parseInt(separated[2]);

                                tv_age.setText("Age : " + getAge(year, month, day));


                                tv_branch.setText(BranchName);
                                tv_dept.setText(Department);
                                tv_designation.setText(Designation);


                            } else if (result.equalsIgnoreCase("false")) {
                                pd1.dismiss();
                                Toast.makeText(Nav_List.this, Messages, Toast.LENGTH_SHORT).show();

                            } else {
                                pd1.dismiss();
                                Toast.makeText(Nav_List.this, Messages, Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                        Log.e("ProfileData", "" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd1.dismiss();
                        if (null != error.networkResponse) {
                            System.out.println("====response===" + error);
                        }
                    }
                });
        request1.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        sr2.add(request1);
    }

    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);

    }

    private class QualificationAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<QualificationBean> qualificationBeen;

        public QualificationAdapter(Nav_List nav_list, ArrayList<QualificationBean> qualificationBeen) {
            this.context = nav_list;
            inflater = LayoutInflater.from(context);
            this.qualificationBeen = qualificationBeen;
        }

        @Override
        public int getCount() {
            return qualificationBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return qualificationBeen.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_Qualificationname, tv_Institute_Name, tv_boardname, tv_Main_Subject_Name,
                    tv_Division_Name, tv_Mark_Percentage, tv_Passing_Year, tv_Weightage;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Holder holder = new Holder();

            View view = convertView;

            if (view == null) {
                view = inflater.inflate(R.layout.qualification_list, parent, false);
                holder.tv_Qualificationname = (TextView) view.findViewById(R.id.tv_Qualificationname);
                holder.tv_Institute_Name = (TextView) view.findViewById(R.id.tv_Institute_Name);
                holder.tv_boardname = (TextView) view.findViewById(R.id.tv_boardname);
                holder.tv_Main_Subject_Name = (TextView) view.findViewById(R.id.tv_Main_Subject_Name);
                holder.tv_Division_Name = (TextView) view.findViewById(R.id.tv_Division_Name);
                holder.tv_Mark_Percentage = (TextView) view.findViewById(R.id.tv_Mark_Percentage);
                holder.tv_Passing_Year = (TextView) view.findViewById(R.id.tv_Passing_Year);
                holder.tv_Weightage = (TextView) view.findViewById(R.id.tv_Weightage);


                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            holder.tv_Qualificationname.setText(qualificationBeen.get(position).getQualificationName());
            holder.tv_Institute_Name.setText(qualificationBeen.get(position).getInstituteName());
            holder.tv_boardname.setText(qualificationBeen.get(position).getBoardName());
            holder.tv_Main_Subject_Name.setText(qualificationBeen.get(position).getMainSubjectName());
            holder.tv_Division_Name.setText(qualificationBeen.get(position).getDivisionName());
            holder.tv_Mark_Percentage.setText(qualificationBeen.get(position).getMarksPercentage());
            holder.tv_Passing_Year.setText(qualificationBeen.get(position).getPassingYear());
            holder.tv_Weightage.setText(qualificationBeen.get(position).getWeightage());


            return view;
        }
    }

    private class FamailyAdapter extends BaseAdapter {

        Context context;
        private LayoutInflater inflater;
        private ArrayList<FamailyMembersBean> famailyMembersBeen;

        public FamailyAdapter(Nav_List nav_list, ArrayList<FamailyMembersBean> famailyMembersBeen) {
            this.context = nav_list;
            inflater = LayoutInflater.from(context);
            this.famailyMembersBeen = famailyMembersBeen;
        }

        @Override
        public int getCount() {
            return famailyMembersBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return famailyMembersBeen.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_f_name, tv_relation, tv_f_dob, tv_f_add, tv_f_phone, tv_email, tv_gartutiy, tv_pf_nominee,
                    tv_PensionNominee, tv_MedicalInsurance, tv_MedicalInsNominee;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.faimily_list, parent, false);
                holder.tv_f_name = (TextView) view.findViewById(R.id.tv_f_name);
                holder.tv_relation = (TextView) view.findViewById(R.id.tv_relation);
                holder.tv_f_dob = (TextView) view.findViewById(R.id.tv_f_dob);
                holder.tv_f_add = (TextView) view.findViewById(R.id.tv_f_add);
                holder.tv_f_phone = (TextView) view.findViewById(R.id.tv_f_phone);
                holder.tv_email = (TextView) view.findViewById(R.id.tv_email);
                holder.tv_gartutiy = (TextView) view.findViewById(R.id.tv_gartutiy);
                holder.tv_pf_nominee = (TextView) view.findViewById(R.id.tv_pf_nominee);
                holder.tv_PensionNominee = (TextView) view.findViewById(R.id.tv_PensionNominee);
                holder.tv_MedicalInsurance = (TextView) view.findViewById(R.id.tv_MedicalInsurance);
                holder.tv_MedicalInsNominee = (TextView) view.findViewById(R.id.tv_MedicalInsNominee);


                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            holder.tv_f_name.setText(famailyMembersBeen.get(position).getNameFamily());
            holder.tv_relation.setText(famailyMembersBeen.get(position).getRelation());
            holder.tv_f_dob.setText(famailyMembersBeen.get(position).getDateOfBirth());
            holder.tv_f_add.setText(famailyMembersBeen.get(position).getAddressFamily());
            holder.tv_f_phone.setText(famailyMembersBeen.get(position).getPhoneNoFamily());
            holder.tv_email.setText(famailyMembersBeen.get(position).getEmailFamily());


            if (famailyMembersBeen.get(position).getGartutiyNominee().equalsIgnoreCase("False")) {

                holder.tv_gartutiy.setText("False");
            } else if (famailyMembersBeen.get(position).getGartutiyNominee().equalsIgnoreCase("True")) {
                holder.tv_gartutiy.setText("True");
            } else {
                holder.tv_gartutiy.setText("False");
            }

            if (famailyMembersBeen.get(position).getPfNominee().equalsIgnoreCase("False")) {

                holder.tv_pf_nominee.setText("False");
            } else if (famailyMembersBeen.get(position).getGartutiyNominee().equalsIgnoreCase("True")) {
                holder.tv_pf_nominee.setText("True");
            } else {
                holder.tv_pf_nominee.setText("False");
            }


            if (famailyMembersBeen.get(position).getPensionNominee().equalsIgnoreCase("False")) {

                holder.tv_PensionNominee.setText("False");
            } else if (famailyMembersBeen.get(position).getPensionNominee().equalsIgnoreCase("True")) {
                holder.tv_PensionNominee.setText("True");
            } else {
                holder.tv_PensionNominee.setText("False");
            }

            if (famailyMembersBeen.get(position).getMedicalInsurance().equalsIgnoreCase("False")) {

                holder.tv_MedicalInsurance.setText("False");
            } else if (famailyMembersBeen.get(position).getMedicalInsurance().equalsIgnoreCase("True")) {
                holder.tv_MedicalInsurance.setText("True");
            } else {
                holder.tv_MedicalInsurance.setText("False");
            }

            if (famailyMembersBeen.get(position).getMedicalInsNominee().equalsIgnoreCase("False")) {

                holder.tv_MedicalInsNominee.setText("False");
            } else if (famailyMembersBeen.get(position).getMedicalInsNominee().equalsIgnoreCase("True")) {
                holder.tv_MedicalInsNominee.setText("True");
            } else {
                holder.tv_MedicalInsNominee.setText("False");
            }


            return view;

        }
    }

    private class SkillsAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<SkillsBean> skillsBeen;

        public SkillsAdapter(Nav_List nav_list, ArrayList<SkillsBean> skillsBeen) {
            this.context = nav_list;
            inflater = LayoutInflater.from(context);
            this.skillsBeen = skillsBeen;

        }

        @Override
        public int getCount() {
            return skillsBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return skillsBeen.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_SkillName, tv_SkillRemark, tv_sr_skill;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.skill_list, parent, false);
                holder.tv_SkillName = (TextView) view.findViewById(R.id.tv_SkillName);
                holder.tv_SkillRemark = (TextView) view.findViewById(R.id.tv_SkillRemark);
                holder.tv_sr_skill = (TextView) view.findViewById(R.id.tv_sr_skill);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }

            int total = position + 1;
            holder.tv_sr_skill.setText(String.valueOf(total));
            holder.tv_SkillName.setText(skillsBeen.get(position).getSkillName());
            holder.tv_SkillRemark.setText(skillsBeen.get(position).getSkillRemark());


            return view;
        }
    }

    private class DocAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<DocBean> docBeen;

        public DocAdapter(Nav_List nav_list, ArrayList<DocBean> docBeen) {
            this.context = nav_list;
            inflater = LayoutInflater.from(context);
            this.docBeen = docBeen;

        }

        @Override
        public int getCount() {
            return docBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return docBeen.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_Document_Name, tv_doc_Remark;
            ImageView img_doc_file;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.doc_list, parent, false);
                holder.tv_Document_Name = (TextView) view.findViewById(R.id.tv_Document_Name);
                holder.tv_doc_Remark = (TextView) view.findViewById(R.id.tv_doc_Remark);
                holder.img_doc_file = (ImageView) view.findViewById(R.id.img_doc_file);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            holder.tv_Document_Name.setText(docBeen.get(position).getDocumentName());
            holder.tv_doc_Remark.setText(docBeen.get(position).getRemarks());
            holder.img_doc_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(Nav_List.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.show_image);
                    dialog.setCanceledOnTouchOutside(false);
//                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                    LinearLayout ll_close = (LinearLayout) dialog.findViewById(R.id.ll_close);
                    ZoomableImageView img_show = (ZoomableImageView) dialog.findViewById(R.id.img_show);

                    Glide.with(Nav_List.this).load(docBeen.get(position).getFilePath())
                            .thumbnail(0.5f)
                            .crossFade()
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(img_show);
                    ll_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();


                        }
                    });


                    dialog.show();
                }
            });


            return view;
        }
    }

    private class HobbiesAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<HobbiesBean> hobbiesBeen;

        public HobbiesAdapter(Nav_List nav_list, ArrayList<HobbiesBean> hobbiesBeen) {
            this.context = nav_list;
            inflater = LayoutInflater.from(context);
            this.hobbiesBeen = hobbiesBeen;


        }

        @Override
        public int getCount() {
            return hobbiesBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return hobbiesBeen.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_h_Remark, tv_h_Name, tv_sr_hobby;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.hobbie_list, parent, false);
                holder.tv_sr_hobby = (TextView) view.findViewById(R.id.tv_sr_hobby);
                holder.tv_h_Name = (TextView) view.findViewById(R.id.tv_h_Name);
                holder.tv_h_Remark = (TextView) view.findViewById(R.id.tv_h_Remark);


                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }

            int total = position + 1;
            holder.tv_sr_hobby.setText(String.valueOf(total));
            holder.tv_h_Name.setText(hobbiesBeen.get(position).getHobbiName());
            holder.tv_h_Remark.setText(hobbiesBeen.get(position).getHobbiRemark());


            return view;
        }
    }

    private class AchievementsAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<AchievementsBean> achievementsBeen;


        public AchievementsAdapter(Nav_List nav_list, ArrayList<AchievementsBean> achievementsBeen) {
            this.context = nav_list;
            inflater = LayoutInflater.from(context);
            this.achievementsBeen = achievementsBeen;
        }

        @Override
        public int getCount() {
            return achievementsBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return achievementsBeen.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_AchievementName, tv_AchievementDate, tv_AchievementRemark;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.achievement_list, parent, false);
                holder.tv_AchievementName = (TextView) view.findViewById(R.id.tv_AchievementName);
                holder.tv_AchievementDate = (TextView) view.findViewById(R.id.tv_AchievementDate);
                holder.tv_AchievementRemark = (TextView) view.findViewById(R.id.tv_AchievementRemark);


                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            holder.tv_AchievementName.setText(achievementsBeen.get(position).getAchievementName());
            holder.tv_AchievementDate.setText(achievementsBeen.get(position).getAchievementDate());
            holder.tv_AchievementRemark.setText(achievementsBeen.get(position).getAchievementRemark());


            return view;
        }
    }

    private class WorkingAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<WorkingBean> workingBeen;


        public WorkingAdapter(Nav_List nav_list, ArrayList<WorkingBean> workingBeen) {

            this.context = nav_list;
            inflater = LayoutInflater.from(context);
            this.workingBeen = workingBeen;

        }

        @Override
        public int getCount() {
            return workingBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return workingBeen.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_Employee_Detail, tv_Place, tv_FromDate, tv_ToDate, tv_BasicSalary,
                    tv_TotalSalary, tv_ReasonForLeaving, tv_DesigScope,
                    tv_Supervisor_Detail, tv_Currency, tv_TotalExperience;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.working_list, parent, false);
                holder.tv_Employee_Detail = (TextView) view.findViewById(R.id.tv_Employee_Detail);
                holder.tv_Place = (TextView) view.findViewById(R.id.tv_Place);
                holder.tv_FromDate = (TextView) view.findViewById(R.id.tv_FromDate);
                holder.tv_ToDate = (TextView) view.findViewById(R.id.tv_ToDate);
                holder.tv_BasicSalary = (TextView) view.findViewById(R.id.tv_BasicSalary);
                holder.tv_TotalSalary = (TextView) view.findViewById(R.id.tv_TotalSalary);
                holder.tv_ReasonForLeaving = (TextView) view.findViewById(R.id.tv_ReasonForLeaving);
                holder.tv_DesigScope = (TextView) view.findViewById(R.id.tv_DesigScope);
                holder.tv_Supervisor_Detail = (TextView) view.findViewById(R.id.tv_Supervisor_Detail);
                holder.tv_Currency = (TextView) view.findViewById(R.id.tv_Currency);
                holder.tv_TotalExperience = (TextView) view.findViewById(R.id.tv_TotalExperience);


                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            holder.tv_Employee_Detail.setText(workingBeen.get(position).getEmployerDetail());
            holder.tv_Place.setText(workingBeen.get(position).getPlace());
//            holder.tv_FromDate.setText(workingBeen.get(position).getFromDate());
//            holder.tv_ToDate.setText(workingBeen.get(position).getToDate());
            holder.tv_BasicSalary.setText(workingBeen.get(position).getBasicSalary());
            holder.tv_TotalSalary.setText(workingBeen.get(position).getTotalSalary());
            holder.tv_ReasonForLeaving.setText(workingBeen.get(position).getReasonForLeaving());
            holder.tv_DesigScope.setText(workingBeen.get(position).getDesigScope());
            holder.tv_Supervisor_Detail.setText(workingBeen.get(position).getSupervisiorDetail());
            holder.tv_Currency.setText(workingBeen.get(position).getCurrency());
            holder.tv_TotalExperience.setText(workingBeen.get(position).getTotalExperience());
            try {
                dateTo = formatInput.parse(workingBeen.get(position).getToDate());
                dateFrom = formatInput.parse(workingBeen.get(position).getFromDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String dateStringTO = formatOutput.format(dateTo);
            String dateStringFrom = formatOutput.format(dateFrom);
            holder.tv_FromDate.setText(dateStringFrom);
            holder.tv_ToDate.setText(dateStringTO);


            return view;
        }
    }
}
