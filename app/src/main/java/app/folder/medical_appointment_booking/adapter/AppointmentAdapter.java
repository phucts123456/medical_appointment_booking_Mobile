package app.folder.medical_appointment_booking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import app.folder.medical_appointment_booking.R;
import app.folder.medical_appointment_booking.dto.AppointmentDTO;

public class AppointmentAdapter extends BaseAdapter {
    List<AppointmentDTO> lst;

    public AppointmentAdapter(List<AppointmentDTO> lst) {
        this.lst = lst;
    }

    public void setLst(List<AppointmentDTO> lst) {
        this.lst = lst;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int i) {
        return lst.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.appointment_item, viewGroup, false);
        }
        TextView txtId = view.findViewById(R.id.txtId);
        TextView txtDate = view.findViewById(R.id.txtDate);
        TextView txtApprove = view.findViewById(R.id.txtApprove);
        TextView txtNote = view.findViewById(R.id.txtNote);
        TextView txtResult = view.findViewById(R.id.txtResult);
        AppointmentDTO appointment = lst.get(i);
        txtId.setText("ID: " + String.valueOf(appointment.getId()));
        txtDate.setText(appointment.getAppointmentDate().toString());
        if(appointment.isApproved) {
            txtApprove.setText("Approved");
        }
        else {
            txtApprove.setText("Not Approve");
        }
        txtNote.setText("Note: " + appointment.getNote());
        txtResult.setText("Result: " + appointment.getResult());
        return view;
    }
}