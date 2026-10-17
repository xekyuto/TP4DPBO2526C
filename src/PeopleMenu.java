import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PeopleMenu extends JFrame {
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField tahunlahirField;
    private JComboBox<String> kategoriComboBox;
    private JRadioButton kolektorRadio;
    private JRadioButton kompetitifRadio;
    private JTable peopleTable;
    private JButton addUpdateButton;
    private JButton deleteButton;
    private JButton cancelButton;

    private ButtonGroup fokusGroup; // Untuk mengelompokkan radio button
    private ArrayList<Person> listPerson;
    private int selectedIndex = -1;

    public static void main(String[] args) {
        PeopleMenu menu = new PeopleMenu();
        menu.setSize(750, 600);
        menu.setLocationRelativeTo(null);
        menu.setContentPane(menu.mainPanel);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public PeopleMenu() {
        listPerson = new ArrayList<>();
        populateList();

        // Mengelompokkan radio button agar hanya bisa dipilih salah satu
        fokusGroup = new ButtonGroup();
        fokusGroup.add(kolektorRadio);
        fokusGroup.add(kompetitifRadio);
        kolektorRadio.setSelected(true); // Default value

        // Setup ComboBox sesuai divisi resmi Pokemon TCG
        String[] kategoriData = {"Junior", "Senior", "Master"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        peopleTable.setModel(setTable());
        deleteButton.setVisible(false);

        // Action Listener Add/Update Button
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // Action Listener Delete Button dengan Konfirmasi
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Konfirmasi pop-up sebelum menghapus data
                int confirm = JOptionPane.showConfirmDialog(mainPanel,
                        "Apakah Anda yakin ingin menghapus data ini?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    deleteData();
                }
            }
        });

        // Action Listener Cancel Button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // Mouse Listener untuk memilih baris tabel
        peopleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = peopleTable.getSelectedRow();

                String curId = peopleTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curNama = peopleTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curTahun = peopleTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curKategori = peopleTable.getModel().getValueAt(selectedIndex, 4).toString();
                String curFokus = peopleTable.getModel().getValueAt(selectedIndex, 5).toString();

                idField.setText(curId);
                namaField.setText(curNama);
                tahunlahirField.setText(curTahun);
                kategoriComboBox.setSelectedItem(curKategori);

                if (curFokus.equals("Kolektor")) {
                    kolektorRadio.setSelected(true);
                } else {
                    kompetitifRadio.setSelected(true);
                }

                addUpdateButton.setText("Update");
                deleteButton.setVisible(true);
            }
        });
    }

    public DefaultTableModel setTable() {
        Object[] cols = {"No", "ID", "Nama", "Tahun Lahir", "Kategori", "Fokus"};
        DefaultTableModel model = new DefaultTableModel(null, cols);

        for (int i = 0; i < listPerson.size(); i++) {
            Object[] row = {
                    i + 1,
                    listPerson.get(i).getId(),
                    listPerson.get(i).getNama(),
                    listPerson.get(i).getTahunLahir(),
                    listPerson.get(i).getKategori(),
                    listPerson.get(i).getFokus()
            };
            model.addRow(row);
        }
        return model;
    }

    public void insertData() {
        try {
            String id = idField.getText();
            String nama = namaField.getText();
            int tahun = Integer.parseInt(tahunlahirField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String fokus = kolektorRadio.isSelected() ? "Kolektor" : "Kompetitif";

            listPerson.add(new Person(id, nama, tahun, kategori, fokus));
            peopleTable.setModel(setTable());
            clearForm();
            JOptionPane.showMessageDialog(mainPanel, "Data berhasil ditambahkan");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainPanel, "Tahun lahir harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateData() {
        try {
            String id = idField.getText();
            String nama = namaField.getText();
            int tahun = Integer.parseInt(tahunlahirField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String fokus = kolektorRadio.isSelected() ? "Kolektor" : "Kompetitif";

            listPerson.get(selectedIndex).setId(id);
            listPerson.get(selectedIndex).setNama(nama);
            listPerson.get(selectedIndex).setTahunLahir(tahun);
            listPerson.get(selectedIndex).setKategori(kategori);
            listPerson.get(selectedIndex).setFokus(fokus);

            peopleTable.setModel(setTable());
            clearForm();
            JOptionPane.showMessageDialog(mainPanel, "Data berhasil diubah");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainPanel, "Tahun lahir harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        listPerson.remove(selectedIndex);
        peopleTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(mainPanel, "Data berhasil dihapus");
    }

    public void clearForm() {
        idField.setText("");
        namaField.setText("");
        tahunlahirField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        kolektorRadio.setSelected(true);
        addUpdateButton.setText("Add");
        deleteButton.setVisible(false);
        selectedIndex = -1;
    }

    public void populateList() {
        listPerson.add(new Person("TCG001", "Andi", 2012, "Junior", "Kompetitif"));
        listPerson.add(new Person("TCG002", "Budi", 2008, "Senior", "Kolektor"));
        listPerson.add(new Person("TCG003", "Citra", 1999, "Master", "Kompetitif"));
    }
}
