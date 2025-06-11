package com.example.tothedestination;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AttractionAdapter extends ArrayAdapter<Attraction>{

    //הגדרת התכונות של המחלקה
    //context- שמציג את הרשימה של האטרקציותActivityה
    //שתמשים בcontext בשביל לקבל את הactivity שצריך להציג את רשימת הטיסות, בשביל לקבל את הLayout וכך הadapter יוכל להמיר את קובץ העיצוב לאובייקט View שניתן להציג על המסך, בשביל לשמש גישה לshared preference ובשביל ליצורintent
    //objects- רשימה של האטרקציות
    Context context;
    List<Attraction> objects;

    //objects- רשימה של האטרקציות
    //textViewResourceld- מבצע את קישור הנתונים לרכיבים ספציפיים מה שנותן לי גמישות ושליטה רבה יותר. הוא שומר את הid של הtextView וכו
    //resource- זה הID של קובץ העיצוב, הLayout של פריט בודד ברשימהף מראה איך להציג אותו
    //context- אובייקט המספק מידע על הסביבה, הactivity, צריך אותו כאן כדי לגשת למשאבים ולעשות דברים כמו גישה לshared preference Intent וכו
    //יצירת פעולה בונה המכניסה חלק מהנתונים שהיא מקבלת לתכונותיה
    public AttractionAdapter(Context context, int resource, int textViewResourceld, List<Attraction> objects) {
        //super מעביר למחלקת האב- arrayAdapter את כל המידע שהיא צריכה כדי לדעת איך להציג כל שורה ברשימה
        super(context, resource, textViewResourceld, objects);
        this.context = context;
        this.objects = objects;
    }

    //הפרמטר parent חשוב כדי שהview יקבל את איך הוא אמרו להיראות ולהיות מוצג, את הlayout
    //parent מספק מידע על הlayout של הview, על פריט הטיסה
    //parent- זהו הListView שאליו יתווסף הView שיוחזר מהפעולה.
    //convertView- אם הוא לא null אז הוא לא צריך ליצור רשימה חדשה, אם כבר יש בתוכו משהו למשל attraction הוא פשוט מעדכן את הנתונים אם צריך להציג אותה פעמים או יותר במקום ליצור View חדש
    //position- מייצג את המיקום של הפריט הנוכחי ברשימה
    //נקראת כל פעם שצריך להציג פריט חדש ברשימה, תפקידה היא לייצר את התצוגה,view עסור פריט ספציפי ברשימה
    public View getView(int position, View convertView, ViewGroup parent) {
        //מייצג איך יוצגו המידע בפני המשתמש, ברשימה, מן טבלה
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        // view מייצג את התצוגה של פריט בודד אחד ברשימה,custom_layout, כלומר שורת אטרקציה אחת ברשימה
        //view מייצג תצוגה כלשהי של הכפתורים הטקטסטים וכו
        View view = layoutInflater.inflate(R.layout.custom_layout3, parent, false);
        //ניגשים פה לרכיבים שנמצאים בתוך הListView, כל המידע שנמצא בפריט, באטרקציה אחת, מקשרים בין המשתנים שיצרנו במסך זה לבין הcustomLayout
        TextView tvAtName = (TextView) view.findViewById(R.id.tvAtName);
        TextView tvExplain = (TextView) view.findViewById(R.id.tvExplain);
        ImageView ivProduct = (ImageView) view.findViewById(R.id.ivProduct);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        //לוקחים מהרשימה את הטיסה אשר עכשיו האפליקציה מצביעה עליה, לפי מיקומה ומכניסים את הנתונים שלה לתוך המשתנים
        Attraction temp = objects.get(position);
        tvAtName.setText(temp.getAttName());
        tvExplain.setText(temp.getExplain());
        //משיגים את התמונה מהfirebase
        StorageReference imageRef = storageRef.child(temp.getImage());


        //מאפשר למשתמש לסמן את הטיסה, למלא את הcheckBox
        CheckBox cbChecked = view.findViewById(R.id.isSelected);
        cbChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                 temp.setChecked(isChecked);
            }
        });

        //make it only for the admin to be able to tp to that activity
        //בלחיצה על תמונת המפה הadmin עובר למסך העריכה של האטרקציה
        ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences("myPref", 0);
                boolean isAdmin = sp.getBoolean("CanEditAttraction",false);
                if(isAdmin)
                {
                    Intent i = new Intent(context, DetailsAttractionMain.class);
                    i.putExtra("attKey", temp.getKey());
                    context.startActivity(i);
                }
            }
        });


        //קוד שנועד להוריד תמונה מהfirease storage ולהציג אותה באםליקציה בimageView
        // Download the image as a byte array
        //הגודל המקסימלי לתמונה שמורידים מהstorage
        final long ONE_MEGABYTE = 1024 * 1024;
        //imageRef מייצג הפניה לתמונה בstorage
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Got the image data as a byte array
                //אם הורדת התמונה התבצעה בהצלחה, מקבלים את התמונה במערך
                //הופך את המערך לתמונה
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                // מכניסים את התמונה למשתנה שיצרו
                ivProduct.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // אם ההורדה לא עוברת בהצלחה
            }
        });

        return view;
    }
}
