package com.example.tothedestination;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TripsAdapter  extends ArrayAdapter<vacation> {

    //הגדרת התכונות של המחלקה
    //context- שמציג את הרשימה של הטיסותActivityה
    //שתמשים בcontext בשביל לקבל את הactivity שצריך להציג את רשימת הטיסות, בשביל לקבל את הLayout וכך הadapter יוכל להמיר את קובץ העיצוב לאובייקט View שניתן להציג על המסך, בשביל לשמש גישה לshared preference ובשביל ליצורintent
    //objects- רשימה של הטיסות
    Context context;
    List<vacation> objects;

    //objects- רשימה של הטיולים
    //textViewResourceld- מבצע את קישור הנתונים לרכיבים ספציפיים מה שנותן לי גמישות ושליטה רבה יותר. הוא שומר את הid של הtextView וכו
    //resource- זה הID של קובץ העיצוב, הLayout של פריט בודד ברשימהף מראה איך להציג אותו
    //context- אובייקט המספק מידע על הסביבה, הactivity, צריך אותו כאן כדי לגשת למשאבים ולעשות דברים כמו גישה לshared preference Intent וכו
    //יצירת פעולה בונה המכניסה חלק מהנתונים שהיא מקבלת לתכונותיה
    public TripsAdapter(Context context, int resource, int textViewResourceld, List<vacation> objects) {
        //super מעביר למחלקת האב- arrayAdapter את כל המידע שהיא צריכה כדי לדעת איך להציג כל שורה ברשימה
        super(context, resource, textViewResourceld, objects);
        this.context = context;
        this.objects = objects;
    }

    //הפרמטר parent חשוב כדי שהview יקבל את איך הוא אמרו להיראות ולהיות מוצג, את הlayout
    //parent מספק מידע על הlayout של הview, על פריט הטיסה
    //parent- זהו הListView שאליו יתווסף הView שיוחזר מהפעולה.
    //convertView- אם הוא לא null אז הוא לא צריך ליצור רשימה חדשה, אם כבר יש בתוכו משהו למשל vacations הוא פשוט מעדכן את הנתונים אם צריך להציג אותה פעמים או יותר במקום ליצור View חדש
    //position- מייצג את המיקום של הפריט הנוכחי ברשימה
    //נקראת כל פעם שצריך להציג פריט חדש ברשימה, תפקידה היא לייצר את התצוגה,view עסור פריט ספציפי ברשימה
    public View getView(int position, View convertView, ViewGroup parent) {
        //מייצג איך יוצגו המידע בפני המשתמש, ברשימה, מן טבלה
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        // view מייצג את התצוגה של פריט בודד אחד ברשימה,custom_layout, כלומר שורת טיול אחת ברשימה
        //view מייצג תצוגה כלשהי של הכפתורים הטקטסטים וכו
        View view = layoutInflater.inflate(R.layout.custom_layout2, parent, false);
        //ניגשים פה לרכיבים שנמצאים בתוך הListView, כל המידע שנמצא בפריט, בטיול אחד, מקשרים בין המשתנים שיצרנו במסך זה לבין הcustomLayout
        TextView tvHoursFly = (TextView) view.findViewById(R.id.tvHoursFlight);
        TextView tvCountry = (TextView) view.findViewById(R.id.tvCountry);
        TextView tvAgeOfChild = (TextView) view.findViewById(R.id.tvAgeOfChild);
        TextView tvSeason = (TextView) view.findViewById(R.id.tvSeason);
        TextView tvAttraction = (TextView) view.findViewById(R.id.tvAttraction);
        TextView tvDateFrom = (TextView) view.findViewById(R.id.tvDateFrom);
        TextView tvDateTo = (TextView) view.findViewById(R.id.tvDateTo);
        ImageView ivProduct = (ImageView) view.findViewById(R.id.ivProduct);

        //לוקחים מהרשימה את הטיולים אשר עכשיו האפליקציה מצביעה עליה, לפי מיקומה ומכניסים את הנתונים שלה לתוך המשתנים
        vacation temp = objects.get(position);
        ivProduct.setImageBitmap(temp.getBitmap());
        tvHoursFly.setText(String.valueOf(temp.getHoursFlight()));
        tvCountry.setText(temp.getCountry());
        tvAgeOfChild.setText(temp.getAgeOfChild());
        tvAttraction.setText(temp.getAttraction());
        tvSeason.setText(temp.getSeason());
        // Create a Date object from the timestamp
        Date date = new Date(temp.getFromDate());
        Date date1 = new Date(temp.getToDate());

        //יוצרים format
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault());

        //מכניסים את התאריך כאשר הוא מסוג string
        tvDateFrom.setText(dateFormat.format(date));
        tvDateTo.setText(dateFormat.format(date1));

        //בלחיצה על התמונה המשתמש יעבור למסך הבא, למסך בו רואים את פרטי הטיול וגם שומרים את המפתח של הטיול והמשתמש
        ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, tripData.class);
                i.putExtra("vacUserKey", temp.getKeyUser());
                i.putExtra("vacKey", temp.getKey());
                context.startActivity(i);
            }
        });


        return view;
    }
}
