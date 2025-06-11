package com.example.tothedestination;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tothedestination.Fly;
import com.example.tothedestination.R;

import java.util.List;

public class FlyAdapter extends ArrayAdapter<Fly> {

    //הגדרת התכונות של המחלקה
    //context- שמציג את הרשימה של הטיוליםActivityה
    //שתמשים בcontext בשביל לקבל את הactivity שצריך להציג את רשימת הטיסות, בשביל לקבל את הLayout וכך הadapter יוכל להמיר את קובץ העיצוב לאובייקט View שניתן להציג על המסך, בשביל לשמש גישה לshared preference ובשביל ליצורintent
    //objects- רשימה של טיולים
    Context context;
    List<Fly> objects;

    //objects- רשימה של הטיסות
    //textViewResourceld- מבצע את קישור הנתונים לרכיבים ספציפיים מה שנותן לי גמישות ושליטה רבה יותר. הוא שומר את הid של הtextView וכו
    //resource- זה הID של קובץ העיצוב, הLayout של פריט בודד ברשימהף מראה איך להציג אותו
    //context- אובייקט המספק מידע על הסביבה, הactivity, צריך אותו כאן כדי לגשת למשאבים ולעשות דברים כמו גישה לshared preference Intent וכו
    //יצירת פעולה בונה המכניסה חלק מהנתונים שהיא מקבלת לתכונותיה
    public FlyAdapter(Context context, int resource, int textViewResourceld, List<Fly> objects) {
        //super מעביר למחלקת האב- arrayAdapter את כל המידע שהיא צריכה כדי לדעת איך להציג כל שורה ברשימה
        super(context,resource,textViewResourceld,objects);
        this.context = context;
        this.objects = objects;
    }

    //הפרמטר parent חשוב כדי שהview יקבל את איך הוא אמרו להיראות ולהיות מוצג, את הlayout
    //parent מספק מידע על הlayout של הview, על פריט הטיסה
    //parent- זהו הListView שאליו יתווסף הView שיוחזר מהפעולה.
    //convertView- אם הוא לא null אז הוא לא צריך ליצור רשימה חדשה, אם כבר יש בתוכו משהו למשל flyList הוא פשוט מעדכן את הנתונים אם צריך להציג אותה פעמים או יותר במקום ליצור View חדש
    //position- מייצג את המיקום של הפריט הנוכחי ברשימה
    //נקראת כל פעם שצריך להציג פריט חדש ברשימה, תפקידה היא לייצר את התצוגה,view עסור פריט ספציפי ברשימה
    public View getView(int position, View convertView, ViewGroup parent) {
        //מייצג איך יוצגו המידע בפני המשתמש, ברשימה, מן טבלה
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        // view- מייצג את התצוגה של פריט בודד אחד ברשימה,custom_layout, כלומר שורת טיסה אחת ברשימה
        //view מייצג תצוגה כלשהי של הכפתורים הטקטסטים וכו
        View view = layoutInflater.inflate(R.layout.custom_layout, parent, false);
        //ניגשים פה לרכיבים שנמצאים בתוך הListView, כל המידע שנמצא בפריט, בטיסה אחת, מקשרים בין המשתנים שיצרנו במסך זה לבין הcustomLayout
        TextView tvHoursFly = (TextView) view.findViewById(R.id.tvHoursFlight);
        TextView tvCountry = (TextView) view.findViewById(R.id.tvCountry);
        TextView tvAttraction = (TextView) view.findViewById(R.id.tvAttraction);
        TextView tvAgeOfChild = (TextView) view.findViewById(R.id.tvAgeOfChild);
        TextView tvSeason = (TextView) view.findViewById(R.id.tvSeason);
        ImageView ivProduct = (ImageView) view.findViewById(R.id.ivProduct);
        CheckBox isCheck = (CheckBox) view.findViewById(R.id.isChecked);

        //לוקחים מהרשימה את הטיסה אשר עכשיו האפליקציה מצביעה עליה, לפי מיקומה ומכניסים את הנתונים שלה לתוך המשתנים
        Fly temp = objects.get(position);
        ivProduct.setImageBitmap(temp.getBitmap());
        tvHoursFly.setText(String.valueOf(temp.getHoursFlight()));
        tvCountry.setText(temp.getCountry());
        tvAttraction.setText(temp.getAttraction());
        tvAgeOfChild.setText(temp.getAgeOfChild());
        tvSeason.setText(temp.getSeason());

        //מאפשר למשתמש לסמן את הטיסה, למלא את הcheckBox
        isCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                temp.setChecked(isChecked);
            }
        });

        //בלחיצה על תמונת המפה המתמש יעבור למסך הבא, לפי האם המשתמש הוא admin או לא
        ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences("myPref", 0);
                boolean isAdmin = sp.getBoolean("CanEditAttraction",false);
                if(isAdmin)
                {
                    Intent i = new Intent(context, DetailsFlyMain.class);
                    i.putExtra("flyKey", temp.getKey());
                    context.startActivity(i);
                }
                else
                {
                    Intent i = new Intent(context, after_deleteMain.class);
                    i.putExtra("flyKey", temp.getKey());
                    context.startActivity(i);
                }
            }
        });


        return view;
    }
}
