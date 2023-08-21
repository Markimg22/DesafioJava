import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class App extends JFrame {
    private DefaultListModel<Team> teamListModel = new DefaultListModel<Team>();
    private DefaultListModel<Person> personListModel = new DefaultListModel<Person>();
    private DefaultListModel<Task> taskListModel = new DefaultListModel<Task>();

    private JList<Team> teamList = new JList<Team>(teamListModel);
    private JList<Person> personList = new JList<Person>(personListModel);
    private JList<Task> taskList = new JList<Task>(taskListModel);

    public App() {
        this.setTitle("Gerenciador de Times");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1, 3));

        // Teams Panel
        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BorderLayout());
        teamPanel.add(new JLabel("Times:", JLabel.CENTER), BorderLayout.NORTH);
        teamPanel.add(new JScrollPane(teamList), BorderLayout.CENTER);
        JPanel buttonsTeamPanel = new JPanel();
        buttonsTeamPanel.setLayout(new GridLayout(2, 1));
        JButton createTeam = new JButton("Adicionar Time");
        createTeam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Dialog
                JDialog teamDialog = new JDialog();
                teamDialog.setTitle("Adicionar Time");
                teamDialog.setSize(200, 100);
                teamDialog.setLayout(new GridLayout(2, 1));
                teamDialog.setVisible(true);
                teamDialog.add(new JLabel("Nome do time:"));
                JTextField teamNameTextField = new JTextField();
                teamDialog.add(teamNameTextField);
                JButton addTeam = new JButton("Adicionar");
                // Add
                addTeam.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        Team team = new Team(teamNameTextField.getText());
                        teamListModel.addElement(team);
                        teamNameTextField.setText("");
                    }
                });
                // Back
                JButton backButton = new JButton("Voltar");
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        teamDialog.setVisible(false);
                    }
                });
                teamDialog.add(addTeam);
                teamDialog.add(backButton);
            }
        });
        JButton removeTeam = new JButton("Remover Time");
        removeTeam.addActionListener(new ActionListener() {
            // Remove
            @Override
            public void actionPerformed(ActionEvent e) {
                Team selectedTeam = teamList.getSelectedValue();
                if (selectedTeam != null) {
                    teamListModel.removeElement(selectedTeam);
                }
            }
        });
        buttonsTeamPanel.add(createTeam);
        buttonsTeamPanel.add(removeTeam);
        teamPanel.add(buttonsTeamPanel, BorderLayout.SOUTH);
        teamList.addListSelectionListener(e -> {
            personListModel.clear();
            taskListModel.clear();
            Team selectedTeam = teamList.getSelectedValue();
            if (selectedTeam != null) {
                for (Person person : selectedTeam.persons) {
                    personListModel.addElement(person);
                }
                for (Task task : selectedTeam.tasks) {
                    taskListModel.addElement(task);
                }
            }
        });

        // Persons Panel
        JPanel persoPanel = new JPanel();
        persoPanel.setLayout(new BorderLayout());
        persoPanel.add(new JLabel("Pessoas:", JLabel.CENTER), BorderLayout.NORTH);
        persoPanel.add(new JScrollPane(personList), BorderLayout.CENTER);
        JPanel buttonsPersonPanel = new JPanel();
        buttonsPersonPanel.setLayout(new GridLayout(2, 1));
        JButton createPerson = new JButton("Adicionar Pessoa");
        createPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Dialog
                JDialog personDialog = new JDialog();
                personDialog.setTitle("Adicionar Pessoa");
                personDialog.setSize(300, 200);
                personDialog.setLayout(new GridLayout(3, 1));
                personDialog.setVisible(true);
                personDialog.add(new JLabel("Nome:"));
                JTextField personNameTextField = new JTextField();
                personDialog.add(personNameTextField);
                personDialog.add(new JLabel("Função:"));
                JComboBox<String> personFunctionComboBox = new JComboBox<String>(
                        new String[] { "Desenvolvedor", "Tester", "Designer" });
                personDialog.add(personFunctionComboBox);
                JButton addPerson = new JButton("Adicionar");
                addPerson.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        Team teamSelected = teamList.getSelectedValue();
                        if (teamSelected != null) {
                            String namePerson = personNameTextField.getText();
                            String functionPerson = (String) personFunctionComboBox.getSelectedItem();
                            Person person = new Person(namePerson, functionPerson);
                            teamSelected.persons.add(person);
                            personListModel.addElement(person);
                        }
                    }
                });
                JButton backButton = new JButton("Voltar");
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        personDialog.setVisible(false);
                    }
                });
                personDialog.add(addPerson);
                personDialog.add(backButton);
            }
        });
        JButton removePerson = new JButton("Remover Pessoa");
        removePerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Team selectedTeam = teamList.getSelectedValue();
                Person selectedPerson = personList.getSelectedValue();
                if (selectedTeam != null || selectedPerson != null) {
                    selectedTeam.persons.remove(selectedPerson);
                    personListModel.removeElement(selectedPerson);
                }
            }
        });
        buttonsPersonPanel.add(createPerson);
        buttonsPersonPanel.add(removePerson);
        persoPanel.add(buttonsPersonPanel, BorderLayout.SOUTH);

        // Tasks Panel
        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BorderLayout());
        tasksPanel.add(new JLabel("Tarefas:", JLabel.CENTER), BorderLayout.NORTH);
        tasksPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);
        JPanel buttonsTaskPanel = new JPanel();
        buttonsTaskPanel.setLayout(new GridLayout(2, 1));
        JButton createTask = new JButton("Adicionar Tarefa");
        createTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Dialog
                JDialog taskDialog = new JDialog();
                taskDialog.setTitle("Adicionar Tarefa");
                taskDialog.setSize(300, 300);
                taskDialog.setLayout(new GridLayout(6, 1));
                taskDialog.setVisible(true);
                taskDialog.add(new JLabel("Título:"));
                JTextField taskTitleField = new JTextField();
                taskDialog.add(taskTitleField);
                taskDialog.add(new JLabel("Descrição:"));
                JTextArea taskDescriptionTextArea = new JTextArea();
                taskDialog.add(taskDescriptionTextArea);
                taskDialog.add(new JLabel("Status:"));
                JComboBox<String> taskStatusComboBox = new JComboBox<String>(
                        new String[] { "A FAZER", "FAZENDO", "FEITO" });
                taskDialog.add(taskStatusComboBox);
                taskDialog.add(new JLabel("Prioridade:"));
                JComboBox<String> taskPriorityComboBox = new JComboBox<String>(
                        new String[] { "ALTA", "MODERADA", "BAIXA" });
                taskDialog.add(taskPriorityComboBox);
                taskDialog.add(new JLabel("Prazo: DD/MM/AAAA"));
                JTextField taskEndDateField = new JTextField();
                taskDialog.add(taskEndDateField);
                JButton addPerson = new JButton("Adicionar");
                addPerson.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        Team teamSelected = teamList.getSelectedValue();
                        if (teamSelected != null) {
                            String titleTask = taskTitleField.getText();
                            String descriptionTask = taskDescriptionTextArea.getText();
                            String statusTask = (String) taskStatusComboBox.getSelectedItem();
                            String priorityTask = (String) taskPriorityComboBox.getSelectedItem();
                            String endDateTask = taskEndDateField.getText();
                            Task task = new Task(titleTask, descriptionTask, endDateTask, priorityTask, statusTask);
                            teamSelected.tasks.add(task);
                            taskListModel.addElement(task);
                        }
                    }
                });
                JButton backButton = new JButton("Voltar");
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        taskDialog.setVisible(false);
                    }
                });
                taskDialog.add(addPerson);
                taskDialog.add(backButton);
            }
        });
        JButton removeTask = new JButton("Remover Tarefa");
        removeTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Team selectedTeam = teamList.getSelectedValue();
                Task selectedTask = taskList.getSelectedValue();
                if (selectedTeam != null || selectedTask != null) {
                    selectedTeam.tasks.remove(selectedTask);
                    taskListModel.removeElement(selectedTask);
                }
            }
        });
        buttonsTaskPanel.add(createTask);
        buttonsTaskPanel.add(removeTask);
        tasksPanel.add(buttonsTaskPanel, BorderLayout.SOUTH);

        this.add(teamPanel);
        this.add(persoPanel);
        this.add(tasksPanel);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}
