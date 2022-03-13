package app.folder.medical_appointment_booking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import app.folder.medical_appointment_booking.R;
import app.folder.medical_appointment_booking.dto.AppointmentDTO;
import app.folder.medical_appointment_booking.dto.DoctorDTO;

public class DoctorAdapter extends BaseAdapter {
    List<DoctorDTO> lst;

    public DoctorAdapter(List<DoctorDTO> lst) {
        this.lst = lst;
    }

    public void setLst(List<DoctorDTO> lst) {
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
            view = inflater.inflate(R.layout.doctor_item, viewGroup, false);
        }
        TextView txtId = view.findViewById(R.id.txtId);
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtRank = view.findViewById(R.id.txtRank);
        DoctorDTO doctor = lst.get(i);
        txtId.setText("ID: " + String.valueOf(doctor.getId()));
        txtName.setText("Name: " + doctor.getFullName());
        txtRank.setText("Rank: " + doctor.getAcademicRank());
        return view;
    }
}