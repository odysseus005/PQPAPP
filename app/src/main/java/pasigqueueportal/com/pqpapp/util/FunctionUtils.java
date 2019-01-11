package pasigqueueportal.com.pqpapp.util;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;


public class FunctionUtils {

    public static final String FULL_23_HR_DATE = "yyyy-MM-dd";
    public static final String DATE_ONLY = "MMM dd, yyyy";
    public static final String DATE_NUM_ONLY = "E, MMM dd";


    public static String appointListTimestampMonDate(String date) {


        try {
            SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd");


            Date newDate = spf.parse(date);

            spf= new SimpleDateFormat("MMM dd");
            date = spf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            date = "error";
        }


        return date;
    }

    public static Date minusMinutesToDate(int minutes, Date beforeTime){
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs - (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }

    public static String getNotesIndexByTime(Date aDate){
        int ret = 0;
        SimpleDateFormat localDateFormat = new SimpleDateFormat("hh:mm a");
        String sTime = localDateFormat.format(aDate);
        return new SimpleDateFormat("hh:mm a").format(aDate);

    }

    public static String convertDateToString(String format, Calendar calendar) {
        if (calendar == null) return "";
        return getSimpleDateFormat(format).format(calendar.getTime());
    }

    public static SimpleDateFormat getSimpleDateFormat(String format) {
        return new SimpleDateFormat(format, Locale.ENGLISH);
    }

    public static String appointListTimestampYear(String date) {



        try {


            SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd");


            Date newDate = spf.parse(date);

            spf= new SimpleDateFormat("yyyy");
            date = spf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            date = "error";
        }


        return date;
    }

    public static String convertStatus(String status)
    {

            String returnStats="";
            switch (status)
            {
                case "P":
                    returnStats = "Pending";
                    break;
                case "N":

                    returnStats = "No Show";
                    break;

                case "S":
                    returnStats = "Successful";

                    break;

                case "C":
                    returnStats = "Cancelled";

                    break;

                case "U":
                    returnStats = "Unpaid";

                    break;

                default:
                    returnStats = "Pending";
                    break;

            }



        return returnStats;
    }


    public static String toReadable(String dateToConvert){
        String convertedDate;
        //String[] arr = dateToConvert.split(" ");
        DateFormat targetFormat = new SimpleDateFormat(FULL_23_HR_DATE, Locale.US);
        Date date = null;
        try {
            date = targetFormat.parse(dateToConvert);


            SimpleDateFormat formatter = new SimpleDateFormat(DATE_NUM_ONLY, Locale.US);
            convertedDate = formatter.format(date);
            return convertedDate.toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }


    public static String hour24to12hour(String time)
    {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            final Date dateObj = sdf.parse(time);
            //DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            return new SimpleDateFormat("hh:mm a").format(dateObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static String Dateto12hour(String time)
    {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final Date dateObj = sdf.parse(time);
            //DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            return new SimpleDateFormat("hh:mm a").format(dateObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }


    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    public static class WeekendDisableDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(final CalendarDay day) {



            if (day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ) {
             return true;
            }
            else
                return  false;

//
        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.setDaysDisabled(true);
        }

    }

    public static class EventDecorator implements DayViewDecorator {

        private int color;
        private HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(20, color));
        }
    }


    public static class EventDecoratorAll implements DayViewDecorator {

        private int color;
        public EventDecoratorAll(int color) {
            this.color = color;

        }


        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return true;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(20, color));
        }
    }


}
