package labs.a.s.disk_scheduling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Math.abs;

public class Main2Activity extends AppCompatActivity {

    EditText Etrack;
    int header,pre_request,ncylinder;
    int ntrack=0,he;
    Integer[] track_request = new Integer[100];
    Integer[] track_original = new Integer[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ncylinder = getIntent().getIntExtra("cylinder",0);
        header = getIntent().getIntExtra("header",0);
        pre_request = getIntent().getIntExtra("prerequest",0);

        Etrack = (EditText)findViewById(R.id.trackrequest_edit);
    }


    public void addtrack(View view) {
        //check if empty
        if (TextUtils.isEmpty(Etrack.getText()) )
        {
            Toast.makeText(Main2Activity.this, "Please Enter the track value", Toast.LENGTH_SHORT).show();
        }
        else{
            //Getting data from edittext
            int track = Integer.parseInt(Etrack.getText().toString());
            track_original[ntrack]=track;
            track_request[ntrack]=track;
            ntrack++;
            fcfs();
            sstf();
            sort();
            scan();
            look();
            c_scan();
            clook();
        }
    }

    void sort() {
        for (int i = 0; i < ntrack; i++) {
            if(header >= track_request[i] && header < track_request[i+1])
            {
                he=i;
            }
            for (int j = 0; j < ntrack; j++) {
                if (track_request[i] < track_request[j]) {
                    int t = track_request[i];
                    track_request[i] = track_request[j];
                    track_request[j] = t;
                }
            }
        }

    }


    void fcfs()
    {
        int sum = 0;
        Integer sequence[] = new Integer[100];
        int head = header,i;
        for(i=0;i<ntrack;i++)
        {

            sequence[i]=head-track_request[i];
            if(sequence[i]<0)
            {
                sequence[i]=track_request[i]-head;
            }
            head=track_request[i];
            sum=sum+sequence[i];
        }
    }

    void sstf()
    {
        int sum=0;
        int cheader=header;
        Integer sequence[] = new Integer[100];
        Boolean inc[]=new Boolean[100];
        for(int i=0;i<ntrack;i++) {
            inc[i] = false;
        }

        for(int i=0;i<ntrack;i++)
        {
            int min=999;
            int index=0;
            for(int j=0;j<ntrack;j++)
            {
                if(abs(cheader-track_request[j])<min && inc[j]==false)
                {
                    min=abs(cheader-track_request[j]);
                    index=j;
                }
            }
            if(inc[index]==false)
            {
                sequence[i]=track_request[index];//ans sequese array
                inc[index]=true;
                sum=sum+min;
                cheader=track_request[index];
            }
        }

    }



    void scan()
    {
        Integer[] current_header = new Integer[100];
        int k=1;
        current_header[0]=header;
        if(header >= pre_request)
        {

            for(int i=he+1;i<ntrack;i++)
            {
                current_header[k]=track_request[i];
                k++;
                if(i==ntrack-1)
                {
                    current_header[k]=ncylinder-1;
                    k++;
                    break;
                }
            }
            for(int i=he;i>=0;i--)
            {
                current_header[k]=track_request[i];
                k++;
            }
        }

        else
        {

            for(int i=he;i>=0;i--)
            {
                current_header[k]=track_request[i];
                k++;
                if(i==0)
                {
                    current_header[k]=0;
                    k++;
                    break;
                }
            }
            for(int i=he+1;i<ntrack;i++)
            {
                current_header[k]=track_request[i];
                k++;
            }

        }

        int seek_time=0;
        for(int i=0;i<ntrack+1;i++)
        {
            seek_time+=abs(current_header[i]-current_header[i+1]);
        }

    }




    void look()
    {
        Integer[] current_header = new Integer[100];
        int k=1;
        current_header[0]=header;
        if(header >= pre_request)
        {

            for(int i=he+1;i<ntrack;i++)
            {
                current_header[k]=track_request[i];
                k++;

            }
            for(int i=he;i>=0;i--)
            {
                current_header[k]=track_request[i];
                k++;
            }
        }

        else
        {
             k=0;
            for(int i=he;i>=0;i--)
            {
                current_header[k]=track_request[i];
                k++;

            }
            for(int i=he+1;i<ntrack;i++)
            {
                current_header[k]=track_request[i];
                k++;
            }

        }



        int seek_time=0;

        for(int i=0;i<ntrack;i++)
        {
            seek_time+=abs(current_header[i]-current_header[i+1]);

        }


    }


    void c_scan()
    {
        Integer[] current_header = new Integer[100];
        int k=1;
        current_header[0]=header;
        if(header >= pre_request)
        {


            for(int i=he+1;i<ntrack;i++)
            {
                current_header[k]=track_request[i];
                k++;
                if(i==ntrack-1)
                {
                    current_header[k]=ncylinder-1;
                    k++;
                    break;
                }
            }

            for(int i=0;i<=he;i++)
            {
                current_header[k]=track_request[i];
                k++;
            }
        }

        else
        {

            for(int i=he;i>=0;i--)
            {
                current_header[k]=track_request[i];
                k++;
                if(i==0)
                {
                    current_header[k]=0;
                    k++;
                    break;
                }
            }
            for(int i=ntrack-1;i>=he;i++)
            {
                current_header[k]=track_request[i];
                k++;
            }

        }



        int seek_time=0;

        for(int i=0;i<ntrack+1;i++)
        {
            seek_time+=abs(current_header[i]-current_header[i+1]);

        }

    }


    void clook()
    {
        Integer[] current_header = new Integer[100];
        int k=1;
        current_header[0]=header;
        if(header >= pre_request)
        {

            for(int i=he+1;i<ntrack;i++)
            {
                current_header[k]=track_request[i];
                k++;

            }
            for(int i=0;i<=he;i++)
            {
                current_header[k]=track_request[i];
                k++;
            }
        }

        else
        {
            k=0;
            for(int i=he;i>=0;i--)
            {
                current_header[k]=track_request[i];
                k++;

            }
            for(int i=ntrack-1;i>he;i--)
            {
                current_header[k]=track_request[i];
                k++;
            }

        }
        int seek_time=0;

        for(int i=0;i<ntrack;i++)
        {
            seek_time+=abs(current_header[i]-current_header[i+1]);

        }

    }
}
