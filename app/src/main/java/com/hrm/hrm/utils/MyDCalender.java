package com.hrm.hrm.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hrm.hrm.CalenderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyDCalender  {

//    private Context context;
//    private AttributeSet attrs;
//    private View rootView;
//
//    private RecyclerView recyclerView_dates, recyclerView_hours, recyclerView_show_events, recyclerView_month_view_below_events;
//    public TextView tv_month_year, tv_mon, tv_tue, tv_wed, tv_thu, tv_fri, tv_sat, tv_sun;
//    private ImageView iv_previous, iv_next;
//    private LinearLayout parentLayout, ll_upper_part, ll_lower_part, ll_blank_space, ll_header_views, ll_week_day_layout, ll_month_view_below_events, ll_hours;
//    public String Ndate = "0";
//    private OnDateClickListener onDateClickListener;
//    private OnEventClickListener onEventClickListener;
//    private OnWeekDayViewClickListener onWeekDayViewClickListener;
//    private GetEventListListener getEventListListener;
//
//    private ArrayList<DateModel> dateModelList;
//    private DateListAdapter dateListAdapter;
//    private ArrayList<EventModel> eventModelList;
//    private EventListAdapter eventListAdapter;
//    private ArrayList<String> hourList;
//    private ArrayList<ShowEventsModel> showEventsModelList;
//    private ShowWeekViewEventsListAdapter showWeekViewEventsListAdapter;
//    private ShowDayViewEventsListAdapter showDayViewEventsListAdapter;
//    private HourListAdapter hourListAdapter;
//
//    private SimpleDateFormat sdfMonthYear = new SimpleDateFormat("MMM - yyyy");
//    private SimpleDateFormat sdfWeekDay = new SimpleDateFormat("dd MMM");
//    private SimpleDateFormat sdfDayMonthYear = new SimpleDateFormat("EEEE,  dd - MMM - yyyy");
//
//    private Calendar calendar = Calendar.getInstance();
//
//    CalenderView calenderView;
//    String monthcount;
//
////    private String strHeaderBackgroundColor, strHeaderTextColor;
//
//    public MyDCalender(Context context) {
//        super(context);
//
//        this.context = context;
//
//        if (!isInEditMode()) {
//            init();
//        }
//    }
//
//    public MyDCalender(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//        this.context = context;
//        this.attrs = attrs;
//
//        if (!isInEditMode()) {
//            init();
//        }
//    }
//
//    private void init() {
//
//        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, com.desai.vatsal.mydynamiccalendar.R.styleable.MyDynamicCalendar, 0, 0);
//
//        try {
////            strHeaderBackgroundColor = a.getString(R.styleable.MyCalendar_headerBackgroundColor);
////            strHeaderTextColor = a.getString(R.styleable.MyCalendar_headerTextColor);
//        } finally {
//            a.recycle();
//        }
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        rootView = inflater.inflate(com.desai.vatsal.mydynamiccalendar.R.layout.my_dynamic_calendar, this, true);
//
//        recyclerView_dates = (RecyclerView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.recyclerView_dates);
//        recyclerView_hours = (RecyclerView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.recyclerView_hours);
//        recyclerView_show_events = (RecyclerView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.recyclerView_show_events);
//        recyclerView_month_view_below_events = (RecyclerView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.recyclerView_month_view_below_events);
//        iv_previous = (ImageView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.iv_previous);
//        iv_next = (ImageView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.iv_next);
//        parentLayout = (LinearLayout) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.parentLayout);
//        ll_upper_part = (LinearLayout) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.ll_upper_part);
//        ll_lower_part = (LinearLayout) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.ll_lower_part);
//        ll_blank_space = (LinearLayout) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.ll_blank_space);
//        ll_header_views = (LinearLayout) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.ll_header_views);
//        ll_week_day_layout = (LinearLayout) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.ll_week_day_layout);
//        ll_month_view_below_events = (LinearLayout) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.ll_month_view_below_events);
//        ll_hours = (LinearLayout) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.ll_hours);
//        tv_month_year = (TextView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.tv_month_year);
//        tv_mon = (TextView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.tv_mon);
//        tv_tue = (TextView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.tv_tue);
//        tv_wed = (TextView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.tv_wed);
//        tv_thu = (TextView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.tv_thu);
//        tv_fri = (TextView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.tv_fri);
//        tv_sat = (TextView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.tv_sat);
//        tv_sun = (TextView) rootView.findViewById(com.desai.vatsal.mydynamiccalendar.R.id.tv_sun);
//
//        calenderView = new CalenderView();
////        ll_header_views.setBackgroundColor(Color.parseColor(strHeaderBackgroundColor));
////        tv_month_year.setTextColor(Color.parseColor(strHeaderTextColor));
//
//
//        actionListeners();
//    }
//
//    private void actionListeners() {
//
//        iv_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonth) {
//                    setMonthView("add");
//                } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonthWithBellowEvents) {
//                    setMonthViewWithBelowEvents("add");
//                } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowWeek) {
//                    setWeekView("add");
//                } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowDay) {
//                    setDayView("add");
//                } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isAgenda) {
//                    setAgendaView("add");
//                }
//
//            }
//        });
//
//        iv_previous.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonth) {
//                    setMonthView("sub");
//                } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonthWithBellowEvents) {
//                    setMonthViewWithBelowEvents("sub");
//                } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowWeek) {
//                    setWeekView("sub");
//                } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowDay) {
//                    setDayView("sub");
//                } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isAgenda) {
//                    setAgendaView("sub");
//                }
//
//            }
//        });
//
//
//    }
//
//    public void setCalendarBackgroundColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            parentLayout.setBackgroundColor(Color.parseColor(color));
//        }
//    }
//
//    public void setCalendarBackgroundColor(int color) {
//        parentLayout.setBackgroundColor(color);
//    }
//
//    public void setHeaderBackgroundColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            ll_header_views.setBackgroundColor(Color.parseColor(color));
//        }
//    }
//
//    public void setHeaderBackgroundColor(int color) {
//        ll_header_views.setBackgroundColor(color);
//    }
//
//    public void setHeaderTextColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            tv_month_year.setTextColor(Color.parseColor(color));
//        }
//    }
//
//    public void setHeaderTextColor(int color) {
//        tv_month_year.setTextColor(color);
//    }
//
//    public void setNextPreviousIndicatorColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            iv_previous.setColorFilter(Color.parseColor(color));
//            iv_next.setColorFilter(Color.parseColor(color));
//        }
//    }
//
//    public void setNextPreviousIndicatorColor(int color) {
//        iv_previous.setColorFilter(color);
//        iv_next.setColorFilter(color);
//    }
//
//    public void setWeekDayLayoutBackgroundColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            ll_week_day_layout.setBackgroundColor(Color.parseColor(color));
//        }
//    }
//
//    public void setWeekDayLayoutBackgroundColor(int color) {
//        ll_week_day_layout.setBackgroundColor(color);
//    }
//
//    public void setWeekDayLayoutTextColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            tv_mon.setTextColor(Color.parseColor(color));
//            tv_tue.setTextColor(Color.parseColor(color));
//            tv_wed.setTextColor(Color.parseColor(color));
//            tv_thu.setTextColor(Color.parseColor(color));
//            tv_fri.setTextColor(Color.parseColor(color));
//            if (!com.desai.vatsal.mydynamiccalendar.AppConstants.isSaturdayOff) {
//                tv_sat.setTextColor(Color.parseColor(color));
//            }
//            if (!com.desai.vatsal.mydynamiccalendar.AppConstants.isSundayOff) {
//                tv_sun.setTextColor(Color.parseColor(color));
//            }
//        }
//    }
//
//    public void setWeekDayLayoutTextColor(int color) {
//        tv_mon.setTextColor(color);
//        tv_tue.setTextColor(color);
//        tv_wed.setTextColor(color);
//        tv_thu.setTextColor(color);
//        tv_fri.setTextColor(color);
//        if (!com.desai.vatsal.mydynamiccalendar.AppConstants.isSaturdayOff) {
//            tv_sat.setTextColor(color);
//        }
//        if (!com.desai.vatsal.mydynamiccalendar.AppConstants.isSundayOff) {
//            tv_sun.setTextColor(color);
//        }
//    }
//
//    public void isSaturdayOff(boolean b, String layoutcolor, String textcolor) {
//        if (b) {
//            if (!TextUtils.isEmpty(layoutcolor) && !TextUtils.isEmpty(textcolor)) {
//                com.desai.vatsal.mydynamiccalendar.AppConstants.isSaturdayOff = true;
//                com.desai.vatsal.mydynamiccalendar.AppConstants.strSaturdayOffBackgroundColor = layoutcolor;
//                com.desai.vatsal.mydynamiccalendar.AppConstants.strSaturdayOffTextColor = textcolor;
//                tv_sat.setTextColor(Color.parseColor(textcolor));
//            }
//        }
//    }
//
//    public void isSaturdayOff(boolean b, int layoutColor, int textColor) {
//        if (b) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.isSaturdayOff = true;
//            com.desai.vatsal.mydynamiccalendar.AppConstants.saturdayOffBackgroundColor = layoutColor;
//            com.desai.vatsal.mydynamiccalendar.AppConstants.saturdayOffTextColor = textColor;
//            tv_sat.setTextColor(textColor);
//        }
//    }
//
//    public void isSundayOff(boolean b, String layoutcolor, String textcolor) {
//        if (b) {
//            if (!TextUtils.isEmpty(layoutcolor) && !TextUtils.isEmpty(textcolor)) {
//                com.desai.vatsal.mydynamiccalendar.AppConstants.isSundayOff = true;
//                com.desai.vatsal.mydynamiccalendar.AppConstants.strSundayOffBackgroundColor = layoutcolor;
//                com.desai.vatsal.mydynamiccalendar.AppConstants.strSundayOffTextColor = textcolor;
//                tv_sun.setTextColor(Color.parseColor(textcolor));
//            }
//        }
//    }
//
//    public void isSundayOff(boolean b, int layoutColor, int textColor) {
//        if (b) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.isSundayOff = true;
//            com.desai.vatsal.mydynamiccalendar.AppConstants.sundayOffBackgroundColor = layoutColor;
//            com.desai.vatsal.mydynamiccalendar.AppConstants.sundayOffTextColor = textColor;
//            tv_sun.setTextColor(textColor);
//        }
//    }
//
//    public void setExtraDatesOfMonthBackgroundColor(int color) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.extraDatesBackgroundColor = color;
//    }
//
//    public void setExtraDatesOfMonthBackgroundColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.strExtraDatesBackgroundColor = color;
//        }
//    }
//
//    public void setExtraDatesOfMonthTextColor(int color) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.extraDatesTextColor = color;
//    }
//
//    public void setExtraDatesOfMonthTextColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.strExtraDatesTextColor = color;
//        }
//    }
//
//    public void setDatesOfMonthBackgroundColor(int color) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.datesBackgroundColor = color;
//    }
//
//    public void setDatesOfMonthBackgroundColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.strDatesBackgroundColor = color;
//        }
//    }
//
//    public void setDatesOfMonthTextColor(int color) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.datesTextColor = color;
//    }
//
//    public void setDatesOfMonthTextColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.strDatesTextColor = color;
//        }
//    }
//
//    public void setCurrentDateBackgroundColor(int color) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.currentDateBackgroundColor = color;
//    }
//
//    public void setCurrentDateBackgroundColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.strCurrentDateBackgroundColor = color;
//        }
//    }
//
//    public void setCurrentDateTextColor(int color) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.currentDateTextColor = color;
//    }
//
//    public void setCurrentDateTextColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.strCurrentDateTextColor = color;
//        }
//    }
//
//    public void setEventCellBackgroundColor(int color) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.eventCellBackgroundColor = color;
//    }
//
//    public void setEventCellBackgroundColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.strEventCellBackgroundColor = color;
//        }
//    }
//
//    public void setEventCellTextColor(int color) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.eventCellTextColor = color;
//    }
//
//    public void setEventCellTextColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.strEventCellTextColor = color;
//        }
//    }
//
//    public void addEvent(String date, String startTime, String endTime, String name) {
//        if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime) && !TextUtils.isEmpty(name)) {
//            String tmpDate = GlobalMethods.convertDate(date, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfDate, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfDate);
//            String tmpStartTime = GlobalMethods.convertDate(startTime, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHourMinute, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHourMinute);
//            String tmpEndTime = GlobalMethods.convertDate(endTime, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHourMinute, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHourMinute);
//            com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.add(new EventModel(tmpDate, tmpStartTime, tmpEndTime, name));
//        }
//    }
//
//    public void addEvent(String date, String startTime, String endTime, String name, int image) {
//        if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime) && !TextUtils.isEmpty(name)) {
//            String tmpDate = GlobalMethods.convertDate(date, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfDate, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfDate);
//            String tmpStartTime = GlobalMethods.convertDate(startTime, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHourMinute, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHourMinute);
//            String tmpEndTime = GlobalMethods.convertDate(endTime, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHourMinute, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHourMinute);
//            com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.add(new EventModel(tmpDate, tmpStartTime, tmpEndTime, name, image));
//        }
//    }
//
//    public void getEventList(GetEventListListener getEventListListener) {
//        this.getEventListListener = getEventListListener;
//        this.getEventListListener.eventList(com.desai.vatsal.mydynamiccalendar.AppConstants.eventList);
//    }
//
//    public void updateEvent(int position, String date, String startTime, String endTime, String name) {
//        if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime) && !TextUtils.isEmpty(name)) {
//            String tmpDate = GlobalMethods.convertDate(date, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfDate, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfDate);
//            String tmpStartTime = GlobalMethods.convertDate(startTime, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHourMinute, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHourMinute);
//            String tmpEndTime = GlobalMethods.convertDate(endTime, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHourMinute, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHourMinute);
//            com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(position).setStrDate(tmpDate);
//            com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(position).setStrStartTime(tmpStartTime);
//            com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(position).setStrEndTime(tmpEndTime);
//            com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(position).setStrName(name);
//        }
//        refreshCalendar();
//    }
//
//    public void deleteEvent(int position) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.remove(position);
//        refreshCalendar();
//    }
//
//    public void deleteAllEvent() {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.clear();
////        refreshCalendar();
//    }
//
//    public void setBelowMonthEventTextColor(int color) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.belowMonthEventTextColor = color;
//    }
//
//    public void setBelowMonthEventTextColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.strBelowMonthEventTextColor = color;
//        }
//    }
//
//    public void setBelowMonthEventDividerColor(int color) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.belowMonthEventDividerColor = color;
//    }
//
//    public void setBelowMonthEventDividerColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.strBelowMonthEventDividerColor = color;
//        }
//    }
//
////    -------------------------- All methods of holiday --------------------------
//
//    public void setHolidayCellClickable(boolean b) {
//        if (b) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.isHolidayCellClickable = true;
//        }
//    }
//
//    public void setHolidayCellBackgroundColor(int color) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.holidayCellBackgroundColor = color;
//    }
//
//    public void setHolidayCellBackgroundColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.strHolidayCellBackgroundColor = color;
//        }
//    }
//
//    public void setHolidayCellTextColor(int color) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.holidayCellTextColor = color;
//    }
//
//    public void setHolidayCellTextColor(String color) {
//        if (!TextUtils.isEmpty(color)) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.strHolidayCellTextColor = color;
//        }
//    }
//
//    public void addHoliday(String date) {
//        if (!TextUtils.isEmpty(date)) {
//            String tmpDate = GlobalMethods.convertDate(date, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfDate, com.desai.vatsal.mydynamiccalendar.AppConstants.sdfDate);
//            com.desai.vatsal.mydynamiccalendar.AppConstants.holidayList.add(tmpDate);
//        }
//    }
//
////    -------------------------------------------------------------------------
//
//
//    public void showMonthView() {
//        setMonthView("");
//    }
//
//    private void setMonthView(String sign) {
//        dateModelList = new ArrayList<>();
//        dateListAdapter = new DateListAdapter(context, dateModelList);
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);
//        recyclerView_dates.setLayoutManager(gridLayoutManager);
//
//        recyclerView_dates.setAdapter(dateListAdapter);
//
//        dateListAdapter.setOnDateClickListener(new OnDateClickListener() {
//            @Override
//            public void onClick(Date date) {
//                if (onDateClickListener != null) {
//                    onDateClickListener.onClick(date);
//                }
//            }
//
//            @Override
//            public void onLongClick(Date date) {
//                if (onDateClickListener != null) {
//                    onDateClickListener.onLongClick(date);
//                }
//            }
//        });
//
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonth = true;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonthWithBellowEvents = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowWeek = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowDay = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isAgenda = false;
//
//        ll_upper_part.setVisibility(View.VISIBLE);
//        ll_month_view_below_events.setVisibility(View.GONE);
//        ll_lower_part.setVisibility(View.GONE);
//        ll_blank_space.setVisibility(View.GONE);
//        ll_hours.setVisibility(View.GONE);
//
//        if (sign.equals("add")) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.set(Calendar.MONTH, (com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.get(Calendar.MONTH) + 1));
//        } else if (sign.equals("sub")) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.set(Calendar.MONTH, (com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.get(Calendar.MONTH) - 1));
//        }
//
//        tv_month_year.setText(sdfMonthYear.format(com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.getTime()));
//
//        Ndate = sdfMonthYear.format(com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.getTime());
//        if (Ndate.contains("Jan")) {
//            monthcount = "1";
//            calenderView.DailyAttendence(monthcount,context);
//
////            Toast.makeText(context, Ndate, Toast.LENGTH_SHORT).show();
//
//
//        } else if (Ndate.contains("Feb")) {
//            monthcount = "2";
//            calenderView.DailyAttendence(monthcount,context);
////            Toast.makeText(context, Ndate, Toast.LENGTH_SHORT).show();
//
//        } else if (Ndate.contains("Mar")) {
//            monthcount = "3";
//            calenderView.DailyAttendence(monthcount,context);
////            Toast.makeText(context, Ndate, Toast.LENGTH_SHORT).show();
//
//        } else if (Ndate.contains("Apr")) {
//            monthcount = "4";
//            calenderView.DailyAttendence(monthcount,context);
//
////            Toast.makeText(context, Ndate, Toast.LENGTH_SHORT).show();
//        } else if (Ndate.contains("May")) {
//            monthcount = "5";
//            calenderView.DailyAttendence(monthcount,context);
//
////            Toast.makeText(context, Ndate, Toast.LENGTH_SHORT).show();
//        } else if (Ndate.contains("Jun")) {
//            monthcount = "6";
//            calenderView.DailyAttendence(monthcount,context);
//
////            Toast.makeText(context, Ndate, Toast.LENGTH_SHORT).show();
//        } else if (Ndate.contains("Jul")) {
//            monthcount = "7";
//            calenderView.DailyAttendence(monthcount,context);
//
////            Toast.makeText(context, Ndate, Toast.LENGTH_SHORT).show();
//        } else if (Ndate.contains("Aug")) {
//            monthcount = "8";
//            calenderView.DailyAttendence(monthcount,context);
//
////            Toast.makeText(context, Ndate, Toast.LENGTH_SHORT).show();
//        } else if (Ndate.contains("Sep")) {
//            monthcount = "9";
//            calenderView.DailyAttendence(monthcount,context);
////            Toast.makeText(context, Ndate, Toast.LENGTH_SHORT).show();
//
//        } else if (Ndate.contains("Oct")) {
//            monthcount = "10";
//            calenderView.DailyAttendence(monthcount,context);
////            Toast.makeText(context, Ndate, Toast.LENGTH_SHORT).show();
//
//        } else if (Ndate.contains("Nov")) {
//            monthcount = "11";
//            calenderView.DailyAttendence(monthcount,context);
//
////            Toast.makeText(context, Ndate, Toast.LENGTH_SHORT).show();
//        } else if (Ndate.contains("Dec")) {
//            monthcount = "12";
//            calenderView.DailyAttendence(monthcount,context);
//
////            Toast.makeText(context, Ndate, Toast.LENGTH_SHORT).show();
//        }
//
//
//        //  Toast.makeText(context,Ndate, Toast.LENGTH_SHORT).show();
//
//
//        Log.e("NDATE", ">>>" + Ndate);
//        // set date start of month
//        calendar.setTime(com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.getTime());
//        calendar.set((Calendar.DAY_OF_MONTH), 1);
//
//        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 2;
//
//        if (monthBeginningCell == -1) {
//            calendar.add(Calendar.DAY_OF_MONTH, -6);
//        } else if (monthBeginningCell == 0) {
//            calendar.add(Calendar.DAY_OF_MONTH, -7);
//        } else {
//            calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
//        }
//
//        dateModelList.clear();
//
//        while (dateModelList.size() < 42) {
//            DateModel model = new DateModel();
//            model.setDates(calendar.getTime());
//            model.setFlag("month");
//            dateModelList.add(model);
//
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//
//        dateListAdapter.notifyDataSetChanged();
//    }
//
//    public void showMonthViewWithBelowEvents() {
//        setMonthViewWithBelowEvents("");
//    }
//
//    private void setMonthViewWithBelowEvents(String sign) {
//        dateModelList = new ArrayList<>();
//        dateListAdapter = new DateListAdapter(context, dateModelList);
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);
//        recyclerView_dates.setLayoutManager(gridLayoutManager);
//
//        recyclerView_dates.setAdapter(dateListAdapter);
//
//        dateListAdapter.setOnDateClickListener(new OnDateClickListener() {
//            @Override
//            public void onClick(Date date) {
//                if (onDateClickListener != null) {
//                    onDateClickListener.onClick(date);
//                }
//            }
//
//            @Override
//            public void onLongClick(Date date) {
//                if (onDateClickListener != null) {
//                    onDateClickListener.onLongClick(date);
//                }
//            }
//        });
//
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonth = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonthWithBellowEvents = true;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowWeek = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowDay = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isAgenda = false;
//
//        ll_upper_part.setVisibility(View.VISIBLE);
//        ll_month_view_below_events.setVisibility(View.VISIBLE);
//        ll_lower_part.setVisibility(View.GONE);
//        ll_blank_space.setVisibility(View.GONE);
//        ll_hours.setVisibility(View.GONE);
//
//        if (sign.equals("add")) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.set(Calendar.MONTH, (com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.get(Calendar.MONTH) + 1));
//        } else if (sign.equals("sub")) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.set(Calendar.MONTH, (com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.get(Calendar.MONTH) - 1));
//        }
//
//        tv_month_year.setText(sdfMonthYear.format(com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.getTime()));
//        Ndate = sdfMonthYear.format(com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.getTime());
//        // set date start of month
//        calendar.setTime(com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.getTime());
//        calendar.set((Calendar.DAY_OF_MONTH), 1);
//
//        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 2;
//
//        if (monthBeginningCell == -1) {
//            calendar.add(Calendar.DAY_OF_MONTH, -6);
//        } else if (monthBeginningCell == 0) {
//            calendar.add(Calendar.DAY_OF_MONTH, -7);
//        } else {
//            calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
//        }
//
//        dateModelList.clear();
//
//        while (dateModelList.size() < 42) {
//            DateModel model = new DateModel();
//            model.setDates(calendar.getTime());
//            model.setFlag("month");
//            dateModelList.add(model);
//
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//
//        dateListAdapter.notifyDataSetChanged();
//
//        dateListAdapter.setOnMonthBellowEventsClick(new OnMonthBellowEventsDateClickListener() {
//            @Override
//            public void onClick(Date date) {
//
//                eventModelList = new ArrayList<>();
//                eventListAdapter = new EventListAdapter(context, eventModelList, "month");
//
//                LinearLayoutManager layoutManagerForShowEventList = new LinearLayoutManager(context);
//                recyclerView_month_view_below_events.setLayoutManager(layoutManagerForShowEventList);
//
//                recyclerView_month_view_below_events.setAdapter(eventListAdapter);
//
//                for (int i = 0; i < com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.size(); i++) {
//                    if (com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(i).getStrDate().equals(com.desai.vatsal.mydynamiccalendar.AppConstants.sdfDate.format(date))) {
//                        eventModelList.add(new EventModel(com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(i).getStrDate(), com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(i).getStrStartTime(), com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(i).getStrEndTime(), com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(i).getStrName()));
//                    }
//                }
//
//                eventListAdapter.notifyDataSetChanged();
//            }
//        });
//    }
//
//    public void showWeekView() {
//        setWeekView("");
//    }
//
//    private void setWeekView(String sign) {
//        dateModelList = new ArrayList<>();
//        dateListAdapter = new DateListAdapter(context, dateModelList);
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);
//        recyclerView_dates.setLayoutManager(gridLayoutManager);
//
//        recyclerView_dates.setAdapter(dateListAdapter);
//
//        dateListAdapter.setOnDateClickListener(new OnDateClickListener() {
//            @Override
//            public void onClick(Date date) {
//                if (onDateClickListener != null) {
//                    onDateClickListener.onClick(date);
//                }
//            }
//
//            @Override
//            public void onLongClick(Date date) {
//                if (onDateClickListener != null) {
//                    onDateClickListener.onLongClick(date);
//                }
//            }
//        });
//
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonth = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonthWithBellowEvents = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowWeek = true;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowDay = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isAgenda = false;
//
//        showEventsModelList = new ArrayList<>();
//        showWeekViewEventsListAdapter = new ShowWeekViewEventsListAdapter(context, showEventsModelList);
//
//        GridLayoutManager gridLayoutManagerForShowEventList = new GridLayoutManager(context, 7);
//        recyclerView_show_events.setLayoutManager(gridLayoutManagerForShowEventList);
//
//        recyclerView_show_events.setAdapter(showWeekViewEventsListAdapter);
//
//        showWeekViewEventsListAdapter.setOnWeekDayViewClickListener(new OnWeekDayViewClickListener() {
//            @Override
//            public void onClick(String date, String time) {
//                if (onWeekDayViewClickListener != null) {
//                    onWeekDayViewClickListener.onClick(date, time);
//                }
//            }
//
//            @Override
//            public void onLongClick(String date, String time) {
//                if (onWeekDayViewClickListener != null) {
//                    onWeekDayViewClickListener.onLongClick(date, time);
//                }
//            }
//        });
//
//        ll_upper_part.setVisibility(View.VISIBLE);
//        ll_month_view_below_events.setVisibility(View.GONE);
//        ll_lower_part.setVisibility(View.VISIBLE);
//        ll_blank_space.setVisibility(View.VISIBLE);
//        ll_hours.setVisibility(View.VISIBLE);
//
//        recyclerView_show_events.setVisibility(VISIBLE);
//
//        setHourList();
//
////        setWeekView("");
//
//        if (sign.equals("add")) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.set(Calendar.DAY_OF_MONTH, (com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.get(Calendar.DAY_OF_MONTH) + 7));
//        } else if (sign.equals("sub")) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.set(Calendar.DAY_OF_MONTH, (com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.get(Calendar.DAY_OF_MONTH) - 7));
//        }
//
//
//        // set date start of month
//        calendar.setTime(com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.getTime());
//
//        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 2;
//        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
//
//        String weekStartDay = sdfWeekDay.format(calendar.getTime());
//
//        dateModelList.clear();
//
//        while (dateModelList.size() < 7) {
//            DateModel model = new DateModel();
//            model.setDates(calendar.getTime());
//            model.setFlag("week");
//            dateModelList.add(model);
//
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//
//        dateListAdapter.notifyDataSetChanged();
//
//        calendar.add(Calendar.DAY_OF_MONTH, -1);
//
//        String weekEndDay = sdfWeekDay.format(calendar.getTime());
//
//        tv_month_year.setText(String.format("%s - %s", weekStartDay, weekEndDay));
//
//
//        showEventsModelList.clear();
//
//        int count7 = 1;
//
//        calendar.add(Calendar.DAY_OF_MONTH, -6);
//
//        Calendar calendar_set_hours = Calendar.getInstance();
//        calendar_set_hours.set(Calendar.HOUR_OF_DAY, 0);
//
//        while (showEventsModelList.size() < 168) {
//            if (count7 < 7) {
//                ShowEventsModel model = new ShowEventsModel();
//                model.setDates(calendar.getTime());
//                model.setHours(com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHour.format(calendar_set_hours.getTime()) + ":00");
//                showEventsModelList.add(model);
//
//                calendar.add(Calendar.DAY_OF_MONTH, 1);
//
//                count7++;
//            } else {
//                ShowEventsModel model = new ShowEventsModel();
//                model.setDates(calendar.getTime());
//                model.setHours(com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHour.format(calendar_set_hours.getTime()) + ":00");
//                showEventsModelList.add(model);
//
//                calendar.add(Calendar.DAY_OF_MONTH, -6);
//
//                calendar_set_hours.add(Calendar.HOUR_OF_DAY, 1);
//
//                count7 = 1;
//            }
//        }
//
//        showWeekViewEventsListAdapter.notifyDataSetChanged();
//    }
//
//    public void showDayView() {
//        setDayView("");
//    }
//
//    private void setDayView(String sign) {
//        showEventsModelList = new ArrayList<>();
//        showDayViewEventsListAdapter = new ShowDayViewEventsListAdapter(context, showEventsModelList);
//
//        LinearLayoutManager layoutManagerForShowEventList = new LinearLayoutManager(context);
//        recyclerView_show_events.setLayoutManager(layoutManagerForShowEventList);
//
//        recyclerView_show_events.setAdapter(showDayViewEventsListAdapter);
//
//        showDayViewEventsListAdapter.setOnWeekDayViewClickListener(new OnWeekDayViewClickListener() {
//            @Override
//            public void onClick(String date, String time) {
//                if (onWeekDayViewClickListener != null) {
//                    onWeekDayViewClickListener.onClick(date, time);
//                }
//            }
//
//            @Override
//            public void onLongClick(String date, String time) {
//                if (onWeekDayViewClickListener != null) {
//                    onWeekDayViewClickListener.onLongClick(date, time);
//                }
//            }
//        });
//
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonth = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonthWithBellowEvents = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowWeek = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowDay = true;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isAgenda = false;
//
//        ll_upper_part.setVisibility(View.GONE);
//        ll_month_view_below_events.setVisibility(View.GONE);
//        ll_lower_part.setVisibility(View.VISIBLE);
//        ll_blank_space.setVisibility(View.VISIBLE);
//        ll_hours.setVisibility(View.VISIBLE);
//
//        recyclerView_show_events.setVisibility(VISIBLE);
//
//        setHourList();
//
////        setDayView("");
//
//        if (sign.equals("add")) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.set(Calendar.DAY_OF_MONTH, (com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.get(Calendar.DAY_OF_MONTH) + 1));
//        } else if (sign.equals("sub")) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.set(Calendar.DAY_OF_MONTH, (com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.get(Calendar.DAY_OF_MONTH) - 1));
//        }
//
//        tv_month_year.setText(sdfDayMonthYear.format(com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.getTime()));
//
//        // set date start of month
//        calendar.setTime(com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.getTime());
//
//        dateModelList.clear();
//
//        Calendar calendar_set_hours = Calendar.getInstance();
//        calendar_set_hours.set(Calendar.HOUR_OF_DAY, 0);
//
//        showEventsModelList.clear();
//
//        for (int i = 0; i < hourList.size(); i++) {
//            ShowEventsModel model = new ShowEventsModel();
//            model.setDates(calendar.getTime());
//            model.setHours(com.desai.vatsal.mydynamiccalendar.AppConstants.sdfHour.format(calendar_set_hours.getTime()) + ":00");
//            showEventsModelList.add(model);
//
//            calendar_set_hours.add(Calendar.HOUR_OF_DAY, 1);
//        }
//
//        showDayViewEventsListAdapter.notifyDataSetChanged();
//    }
//
//    public void showAgendaView() {
//        setAgendaView("");
//    }
//
//    private void setAgendaView(String sign) {
//        dateModelList = new ArrayList<>();
//        dateListAdapter = new DateListAdapter(context, dateModelList);
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);
//        recyclerView_dates.setLayoutManager(gridLayoutManager);
//
//        recyclerView_dates.setAdapter(dateListAdapter);
//
//        dateListAdapter.setOnDateClickListener(new OnDateClickListener() {
//            @Override
//            public void onClick(Date date) {
//                if (onDateClickListener != null) {
//                    onDateClickListener.onClick(date);
//                }
//            }
//
//            @Override
//            public void onLongClick(Date date) {
//                if (onDateClickListener != null) {
//                    onDateClickListener.onLongClick(date);
//                }
//            }
//        });
//
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonth = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonthWithBellowEvents = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowWeek = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isShowDay = false;
//        com.desai.vatsal.mydynamiccalendar.AppConstants.isAgenda = true;
//
//        ll_upper_part.setVisibility(View.VISIBLE);
//        ll_month_view_below_events.setVisibility(View.GONE);
//        ll_lower_part.setVisibility(View.VISIBLE);
//        ll_blank_space.setVisibility(View.GONE);
//        ll_hours.setVisibility(View.GONE);
//
//
//        if (sign.equals("add")) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.set(Calendar.DAY_OF_MONTH, (com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.get(Calendar.DAY_OF_MONTH) + 7));
//        } else if (sign.equals("sub")) {
//            com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.set(Calendar.DAY_OF_MONTH, (com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.get(Calendar.DAY_OF_MONTH) - 7));
//        }
//
//
//        // set date start of month
//        calendar.setTime(com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.getTime());
//
//        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 2;
//        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
//
//        String weekStartDay = sdfWeekDay.format(calendar.getTime());
//
//        dateModelList.clear();
//
//        while (dateModelList.size() < 7) {
//            DateModel model = new DateModel();
//            model.setDates(calendar.getTime());
//            model.setFlag("week");
//            dateModelList.add(model);
//
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//
//        dateListAdapter.notifyDataSetChanged();
//
//        calendar.add(Calendar.DAY_OF_MONTH, -1);
//
//        String weekEndDay = sdfWeekDay.format(calendar.getTime());
//
//        tv_month_year.setText(String.format("%s - %s", weekStartDay, weekEndDay));
//
//
//        recyclerView_show_events.setVisibility(GONE);
//
//        dateListAdapter.setOnMonthBellowEventsClick(new OnMonthBellowEventsDateClickListener() {
//            @Override
//            public void onClick(Date date) {
//
//                recyclerView_show_events.setVisibility(VISIBLE);
//
//                eventModelList = new ArrayList<EventModel>();
//                eventListAdapter = new EventListAdapter(context, eventModelList, "month");
//
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                recyclerView_show_events.setLayoutManager(linearLayoutManager);
//
//                recyclerView_show_events.setAdapter(eventListAdapter);
//
//                for (int i = 0; i < com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.size(); i++) {
//                    if (com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(i).getStrDate().equals(com.desai.vatsal.mydynamiccalendar.AppConstants.sdfDate.format(date))) {
//                        eventModelList.add(new EventModel(com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(i).getStrDate(), com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(i).getStrStartTime(), com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(i).getStrEndTime(), com.desai.vatsal.mydynamiccalendar.AppConstants.eventList.get(i).getStrName()));
//                    }
//                }
//
//                eventListAdapter.notifyDataSetChanged();
//            }
//        });
//
//    }
//
//    private void setHourList() {
//
//        hourList = new ArrayList<>();
//
//        for (int i = 0; i < 24; i++) {
//            hourList.add(String.format("%02d", i));
//        }
//
//        hourListAdapter = new HourListAdapter(context, hourList);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        recyclerView_hours.setLayoutManager(layoutManager);
//
//        recyclerView_hours.setAdapter(hourListAdapter);
//
//    }
//
//
//    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
//        this.onDateClickListener = onDateClickListener;
//    }
//
//    public void setOnEventClickListener(OnEventClickListener onEventClickListener) {
//        this.onEventClickListener = onEventClickListener;
//    }
//
//    public void setOnWeekDayViewClickListener(OnWeekDayViewClickListener onWeekDayViewClickListener) {
//        this.onWeekDayViewClickListener = onWeekDayViewClickListener;
//    }
//
//    public void goToCurrentDate() {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar = Calendar.getInstance();
//
//        if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonth) {
//            setMonthView("");
//        } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonthWithBellowEvents) {
//            setMonthViewWithBelowEvents("");
//        } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowWeek) {
//            setWeekView("");
//        } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowDay) {
//            setDayView("");
//        } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isAgenda) {
//            setAgendaView("");
//        }
//    }
//
//    public void refreshCalendar() {
//        if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonth) {
//            setMonthView("");
//        } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowMonthWithBellowEvents) {
//            setMonthViewWithBelowEvents("");
//        } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowWeek) {
//            setWeekView("");
//        } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isShowDay) {
//            setDayView("");
//        } else if (com.desai.vatsal.mydynamiccalendar.AppConstants.isAgenda) {
//            setAgendaView("");
//        }
//    }
//
//    public void setCalendarDate(int date, int month, int year) {
//        com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.set(Calendar.YEAR, year);
//        com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.set(Calendar.MONTH, month - 1);
//        com.desai.vatsal.mydynamiccalendar.AppConstants.main_calendar.set(Calendar.DAY_OF_MONTH, date);
//
//        refreshCalendar();
//    }

}
