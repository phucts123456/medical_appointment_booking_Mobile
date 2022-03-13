package app.folder.medical_appointment_booking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import app.folder.medical_appointment_booking.dto.Appointment;
import app.folder.medical_appointment_booking.R;

public class AppointmetntAdapter extends BaseAdapter {
    private List<Appointment> appointmentList;

    public void setAppointmentDTOList(List<Appointment> studentDTOList) {
        this.appointmentList = studentDTOList;
    }

    @Override
    public int getCount() {
        return appointmentList.size();
    }

    @Override
    public Object getItem(int i) {
        return appointmentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.activity_appointment_detail, viewGroup, false);
        }
        TextView txtSpec = view.findViewById(R.id.txtSpec);
        TextView txtStatus = view.findViewById(R.id.txtStatus);
        TextView txtGender = view.findViewById(R.id.txtGender);
        TextView txtDocname = view.findViewById(R.id.txtDocName);
        TextView txtAcaRank = view.findViewById(R.id.txtAcaRank);
        TextView txtNote = view.findViewById(R.id.txtNote);
        TextView edtResult = view.findViewById(R.id.edtResult);


        Appointment dto = appointmentList.get(i);
        txtSpec.setText(dto.getDoctor().getSpec().getName());
        txtDocname.setText(dto.getDoctor().getFullName());
        txtAcaRank.setText(dto.getDoctor().getAcademicRank());
        txtNote.setText(dto.getNote());
        if (dto.isApproved() == true){
            txtStatus.setText("Approved");
        }else if(dto.isApproved() == false){
            txtStatus.setText("Rejected");
        }
        else {
            txtStatus.setText("Processing");

        }
        if (dto.getDoctor().isMale() == true){
            txtGender.setText("Male");
        }else {
            txtGender.setText("Female");
        }
        edtResult.setText(dto.getResult());
        return view;
    }

}
