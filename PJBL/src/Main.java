import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;

class CampoVazioException extends Exception {
    public CampoVazioException(String mensagem) {
        super(mensagem);
    }
}

class Consulta {
    String CPF;
    String especialidade;
    String medico;
    String recepcionista;
    String dataConsulta;
    String horaConsulta;

    public Consulta(String CPF, String especialidade, String medico, String recepcionista, String dataConsulta, String horaConsulta) {
        this.CPF = CPF;
        this.especialidade = especialidade;
        this.medico = medico;
        this.recepcionista = recepcionista;
        this.dataConsulta = dataConsulta;
        this.horaConsulta = horaConsulta;
    }

    public String getCPF() {
        return CPF;
    }
    public String getEspecialidade() {
        return especialidade;
    }
    public String getMedico() {
        return medico;
    }
    public String getRecepcionista() {
        return recepcionista;
    }
    public String getDataConsulta() {
        return dataConsulta;
    }
    public String getHoraConsulta() {
        return horaConsulta;
    }
}

abstract class Pessoa {
    protected String CPF;
    protected String nome;
    protected int idade;

    public Pessoa(String CPF, String nome, int idade) {
        this.CPF = CPF;
        this.nome = nome;
        this.idade = idade;
    }

    public String getCPF() {
        return this.CPF;
    }

    public String getNome() {
        return this.nome;
    }

    public int getIdade() {
        return idade;
    }

    public abstract String exibirDetalhes();
}

class Paciente extends Pessoa {
    private String plano;
    private String recepcionistaCadastro;

    public Paciente(String CPF, String nome, int idade, String plano, String recepcionistaCadastro) {
        super(CPF, nome, idade);
        this.plano = plano;
        this.recepcionistaCadastro = recepcionistaCadastro;
    }

    public String getPlano() {
        return plano;
    }

    public String getRecepcionistaCadastro() {
        return recepcionistaCadastro;
    }

    @Override
    public String exibirDetalhes() {
        return "\nCPF: " + CPF + "\nNome: " + nome + "\nIdade: " + idade + "\nPlano: " + plano + "\nRecepcionista: " + recepcionistaCadastro;
    }
}

class Recepcionista extends Pessoa {
    private String turno;

    public Recepcionista(String CPF, String nome, int idade, String turno) {
        super(CPF, nome, idade);
        this.turno = turno;
    }

    public String getTurno() {
        return turno;
    }

    @Override
    public String exibirDetalhes() {
        return "CPF: " + CPF + "\nNome: " + nome + "\nIdade: " + idade + "\nTurno: " + turno + "\n\n";
    }
}

class Medico extends Pessoa {
    protected static String especialidade;

    public Medico(String CPF, String nome, int idade, String especialidade) {
        super(CPF, nome, idade);
        this.especialidade = especialidade;
    }

    public String realizarConsulta(String nomemedico) {
        return "Consulta está sendo realizada pelo\n" + "Médico: " + nomemedico + "\n\n";
    }

    public String getNome(){
        return this.nome;
    }

    @Override
    public String exibirDetalhes() {
        return "\nCPF: " + CPF + "\nNome: " + nome + "\nIdade: " + idade + "\nEspecialidade: " + especialidade + "\n\n";
    }
}

class Residente extends Medico {
    public Residente (String CPF, String nome, int idade, String especialidade){
        super(CPF, nome, idade, especialidade);
    }

    public String getNome(){
        return this.nome;
    }

    @Override
    public String realizarConsulta(String nomeresidente) {
        return "Consulta está sendo realizada pelo\n" + "Residente: " + nomeresidente + "\n\n";
    }

    @Override
    public String exibirDetalhes() {
        return "\nCPF: " + CPF + "\nNome: " + nome + "\nIdade: " + idade + "\nEspecialização: " + especialidade + "\n\n";
    }
}

class Hospital extends JFrame {
    private ArrayList<Paciente> pacientes = new ArrayList<>();
    private ArrayList<Consulta> consultas = new ArrayList<>();
    private List<String> planosDeSaude;
    private List<Recepcionista> recepcionistas;
    private HashMap<String, List<Medico>> especialidadeMedicos = new HashMap<>();
    private HashMap<String, List<Residente>> especialidadeResidentes = new HashMap<>();
    private JLabel relogioLabel;

    private void criarArquivosSeNaoExistirem() throws IOException {

        File arquivoPacientes = new File("cadastros_pacientes.txt");
        File arquivoConsultas = new File("consultas_marcadas.txt");
        if (arquivoConsultas.exists()) {
            return;
        } else {arquivoConsultas.createNewFile();}
        if (arquivoPacientes.exists()) {
            return;
        } else {arquivoPacientes.createNewFile();}
    }

    public Hospital() throws IOException {
        setTitle("Marcar Consultas - Hospital");
        setSize(1000, 500);
        criarArquivosSeNaoExistirem();
        planosDeSaude = List.of("Bronze", "Prata", "Ouro");
        recepcionistas = List.of(
                new Recepcionista("947.075.750-56", "Mariana", 28, "Dia"),
                new Recepcionista("880.097.710-31", "João", 37, "Tarde"),
                new Recepcionista("586.356.760-09", "Guilherme", 30, "Noite")
        );

        especialidadeMedicos.put("Urologista", List.of(
                new Medico("119.504.780-09", "Dra. Maier", 45, "Urologista"),
                new Medico("593.130.320-07", "Dr. Neuer", 50, "Urologista")
        ));

        especialidadeMedicos.put("Cardiologista", List.of(
                new Medico("963.461.850-20", "Dr. Silva", 45, "Cardiologista"),
                new Medico("961.359.840-55", "Dra. Koultrapali", 50, "Cardiologista")
        ));

        especialidadeMedicos.put("Neurologista", List.of(
                new Medico("526.910.830-55", "Dr. Yamada", 45, "Neurologista"),
                new Medico("257.744.770-10", "Dr. Bloodmoon", 50, "Neurologista")
        ));

        especialidadeMedicos.put("Dermatologista", List.of(
                new Medico("765.356.350-31", "Dr. Brumado", 45, "Dermatologista"),
                new Medico("203.828.350-85", "Dra. Souza", 50, "Dermatologista")
        ));
        especialidadeResidentes.put("Neurologista", List.of(
                new Residente("124.656.893-08", "Dr. Roacutan", 24, "Neurologista"),
                new Residente("456.123.789-08", "Dr. Peneira", 24, "Neurologista")
        ));
        especialidadeResidentes.put("Dermatologista", List.of(
                new Residente("124.656.893-08", "Dr. New", 23, "Dermatologista"),
                new Residente("456.123.789-08", "Dra. Ferraz", 24, "Dermatologista")
        ));
        especialidadeResidentes.put("Cardiologista", List.of(
                new Residente("456.987.412-45", "Dr. Old", 33, "Dermatologista"),
                new Residente("159.753.486-65", "Dra. Jones", 24, "Dermatologista")
        ));


        JPanel painelPrincipal = new JPanel(new BorderLayout());
        JPanel painelCentral = new JPanel(new GridLayout(6, 1, 20, 20));

        JButton botaoCadastrarPaciente = new JButton("Cadastrar Paciente");
        botaoCadastrarPaciente.addActionListener(e -> mostrarFormularioCadastro());

        JButton botaoMarcarConsulta = new JButton("Marcar Consulta");
        botaoMarcarConsulta.addActionListener(e -> mostrarFormularioConsulta());

        JButton botaoVisualizarConsultas = new JButton("Visualizar Consultas");
        botaoVisualizarConsultas.addActionListener(e -> {
            JTextArea consultasArea = new JTextArea();
            JScrollPane consultasScrollPane = new JScrollPane(consultasArea);
            String consultasTexto = carregarConsultas();
            consultasArea.setText(consultasTexto);
            JFrame detalhesFrame = new JFrame("Detalhes das Consultas");
            detalhesFrame.add(consultasScrollPane);
            detalhesFrame.setSize(600, 400);
            detalhesFrame.setVisible(true);
        });

        JButton botaoDetalhesMedicos = new JButton("Detalhes Médicos");
        ArrayList<Medico> medicos = new ArrayList<>();
        for (List<Medico> medico : especialidadeMedicos.values()) {
            medicos.addAll(medico);
        }
        botaoDetalhesMedicos.addActionListener(e -> {
            JTextArea medicosArea = new JTextArea(20, 50);
            medicosArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(medicosArea);
            StringBuilder detalhes = new StringBuilder();
            for (Medico medico : medicos) {
                detalhes.append(medico.exibirDetalhes());
            }
            medicosArea.setText(detalhes.toString());
            JFrame detalhesFrame = new JFrame("Detalhes dos Médicos");
            detalhesFrame.add(scrollPane);
            detalhesFrame.setSize(600, 400);
            detalhesFrame.setVisible(true);
        });

        JButton botaoDetalhesResidentes = new JButton("Detalhes Residentes");
        ArrayList<Residente> residentes = new ArrayList<>();
        for (List<Residente> residente : especialidadeResidentes.values()) {
            residentes.addAll(residente);
        }
        botaoDetalhesResidentes.addActionListener(e -> {
            JTextArea residenteArea = new JTextArea(20, 50);
            residenteArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(residenteArea);
            StringBuilder detalhes = new StringBuilder();
            for (Residente residente : residentes) {
                detalhes.append(residente.exibirDetalhes());
            }
            residenteArea.setText(detalhes.toString());
            JFrame detalhesFrame = new JFrame("Detalhes dos Residentes");
            detalhesFrame.add(scrollPane);
            detalhesFrame.setSize(600, 400);
            detalhesFrame.setVisible(true);
        });

        JButton botaoDetalhesRecepcionistas = new JButton("Detalhes Recepcionistas");
        botaoDetalhesRecepcionistas.addActionListener(e -> {
            JTextArea textArea = new JTextArea(20, 50);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            StringBuilder detalhes = new StringBuilder();
            for (Recepcionista recepcionista : recepcionistas) {
                detalhes.append(recepcionista.exibirDetalhes()).append("\n");
            }
            textArea.setText(detalhes.toString());
            JFrame detalhesFrame = new JFrame("Detalhes dos Recepcionistas");
            detalhesFrame.add(scrollPane);
            detalhesFrame.setSize(600, 400);
            detalhesFrame.setVisible(true);
        });

        JButton botaoDetalhesPacientes = new JButton("Detalhes Pacientes");
        botaoDetalhesPacientes.addActionListener(e -> {
            try {
                carregarPacientes();
            } catch (RuntimeException x) {
                JOptionPane.showMessageDialog(null, "Nenhum Paciente Cadastrado", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JTextArea textArea = new JTextArea(20, 50);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            StringBuilder detalhes = new StringBuilder();
            for (Paciente paciente : pacientes) {
                detalhes.append(paciente.exibirDetalhes()).append("\n");
            }
            textArea.setText(detalhes.toString());
            JFrame detalhesFrame = new JFrame("Detalhes dos Pacientes");
            detalhesFrame.add(scrollPane);
            detalhesFrame.setSize(600, 400);
            detalhesFrame.setVisible(true);
        });

        JButton botaoRealizarConsultas = new JButton("Realizar Consulta");

        botaoRealizarConsultas.addActionListener(e -> {
            JPanel painelConsulta = new JPanel(new GridLayout(2, 1, 20, 20));
            JButton botaoMedico = new JButton("Consulta pelo Médico");
            JButton botaoResidente = new JButton("Consulta pelo Residente");

            painelConsulta.add(botaoMedico);
            painelConsulta.add(botaoResidente);

            botaoMedico.addActionListener(ex -> {
                JPanel painelBuscaMedico = new JPanel(new GridLayout(3, 1, 10, 10));
                JLabel labelMedico = new JLabel("Digite o nome do médico: ");
                JTextField fieldMedico = new JTextField();
                JButton botaoConsultaMedico = new JButton("Realizar Consulta");

                painelBuscaMedico.add(labelMedico);
                painelBuscaMedico.add(fieldMedico);
                painelBuscaMedico.add(botaoConsultaMedico);

                botaoConsultaMedico.addActionListener(exe -> {
                    ArrayList<Medico> medicosConsulta = new ArrayList<>();
                    for (List<Medico> medicoList : especialidadeMedicos.values()) {
                        medicosConsulta.addAll(medicoList);
                    }

                    for (Medico medico : medicosConsulta) {
                        if (medico.getNome().equals(fieldMedico.getText())) {
                            JTextArea medicoArea = new JTextArea();
                            JScrollPane medicoScrollPane = new JScrollPane(medicoArea);
                            String medicoTexto = medico.realizarConsulta(fieldMedico.getText());
                            medicoArea.setText(medicoTexto);
                            JFrame detalhesFrame = new JFrame("Detalhes da Consulta");
                            detalhesFrame.add(medicoScrollPane);
                            detalhesFrame.setSize(600, 400);
                            detalhesFrame.setVisible(true);
                            return;
                        }
                    }

                    JOptionPane.showMessageDialog(painelConsulta, "Médico não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                });

                JOptionPane.showMessageDialog(null, painelBuscaMedico, "Buscar Médico", JOptionPane.PLAIN_MESSAGE);
            });

            botaoResidente.addActionListener(ex -> {
                JPanel painelBuscaResidente = new JPanel(new GridLayout(3, 1, 10, 10));
                JLabel labelResidente = new JLabel("Digite o nome do Residente: ");
                JTextField fieldResidente = new JTextField();
                JButton botaoConsultaResidente = new JButton("Realizar Consulta");

                painelBuscaResidente.add(labelResidente);
                painelBuscaResidente.add(fieldResidente);
                painelBuscaResidente.add(botaoConsultaResidente);

                botaoConsultaResidente.addActionListener(exe -> {
                    ArrayList<Residente> residentesConsulta = new ArrayList<>();
                    for (List<Residente> residenteList : especialidadeResidentes.values()) {
                        residentesConsulta.addAll(residenteList);
                    }

                    for (Residente residente : residentesConsulta) {
                        if (residente.getNome().equals(fieldResidente.getText())) {
                            JTextArea residenteArea = new JTextArea();
                            JScrollPane residenteScrollPane = new JScrollPane(residenteArea);
                            String medicoTexto = residente.realizarConsulta(fieldResidente.getText());
                            residenteArea.setText(medicoTexto);
                            JFrame detalhesFrame = new JFrame("Detalhes da Consulta");
                            detalhesFrame.add(residenteScrollPane);
                            detalhesFrame.setSize(600, 400);
                            detalhesFrame.setVisible(true);
                            return;
                        }
                    }

                    JOptionPane.showMessageDialog(painelConsulta, "Residente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                });

                JOptionPane.showMessageDialog(null, painelBuscaResidente, "Buscar Residente", JOptionPane.PLAIN_MESSAGE);
            });

            JOptionPane.showMessageDialog(null, painelConsulta, "Realizar Consulta", JOptionPane.PLAIN_MESSAGE);
        });






        painelCentral.add(botaoCadastrarPaciente);
        painelCentral.add(botaoMarcarConsulta);
        painelCentral.add(botaoVisualizarConsultas);
        painelCentral.add(botaoDetalhesMedicos);
        painelCentral.add(botaoDetalhesResidentes);
        painelCentral.add(botaoDetalhesRecepcionistas);
        painelCentral.add(botaoDetalhesPacientes);
        painelCentral.add(botaoRealizarConsultas);
        painelPrincipal.add(painelCentral, BorderLayout.CENTER);

        relogioLabel = new JLabel();
        relogioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        painelPrincipal.add(relogioLabel, BorderLayout.NORTH);
        add(painelPrincipal);
        atualizarRelogio();

    }

    public void carregarPacientes(){
        pacientes.clear();
        File arquivo = new File("cadastros_pacientes.txt");
        try {BufferedReader br = new BufferedReader(new FileReader(arquivo));
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length < 5) continue;
                String CPF = dados[0];
                String nome = dados[1];
                int idade = Integer.parseInt(dados[2]);
                String plano = dados[3];
                String recepcionistaCadastro = dados[4];
                pacientes.add(new Paciente(CPF, nome, idade, plano, recepcionistaCadastro));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String carregarConsultas() {
        consultas.clear();
        StringBuilder sb = new StringBuilder();
        File arquivo = new File("consultas_marcadas.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length < 6) continue;
                String CPF = dados[0];
                String especialidade = dados[1];
                String medico = dados[2];
                String recepcionista = dados[3];
                String dataConsulta = dados[4];
                String horaConsulta = dados[5];
                consultas.add(new Consulta(CPF, especialidade, medico, recepcionista, dataConsulta, horaConsulta));
                sb.append("CPF: ").append(CPF)
                        .append(", Especialidade: ").append(especialidade)
                        .append(", Médico: ").append(medico)
                        .append(", Recepcionista: ").append(recepcionista)
                        .append(", Data: ").append(dataConsulta)
                        .append(", Hora: ").append(horaConsulta)
                        .append("\n\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    private void atualizarRelogio() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
                String horaAtual = formatoHora.format(new Date());
                relogioLabel.setText("Hora Atual: " + horaAtual);
            }
        }, 0, 1000);
    }

    private void mostrarFormularioCadastro() {
        JPanel painelFormulario = new JPanel(new GridLayout(7, 1, 20, 20));

        JLabel labelNome = new JLabel("Nome: ");
        JTextField fieldNome = new JTextField();

        JLabel labelCPF = new JLabel("CPF: ");
        JTextField fieldCPF = new JTextField();

        JLabel labelIdade = new JLabel("Idade: ");
        JTextField fieldIdade = new JTextField();

        JLabel labelPlano = new JLabel("Plano de Saúde: ");
        JComboBox<String> comboPlano = new JComboBox<>(planosDeSaude.toArray(new String[0]));

        JLabel labelRecepcionista = new JLabel("Recepcionista: ");
        ArrayList<String> rnome = new ArrayList<>();
        for (Recepcionista recepcionista : recepcionistas) {
            rnome.add(recepcionista.getNome());}
        JComboBox<String> comboRecepcionista = new JComboBox<>(rnome.toArray(new String[0]));

        JButton botaoCadastrarPaciente = new JButton("Cadastrar Paciente");
        botaoCadastrarPaciente.addActionListener(e -> {
            int idade;
            try {
                idade = Integer.parseInt(fieldIdade.getText());
                String nome = fieldNome.getText();
                String cpf = fieldCPF.getText();
                String plano = (String) comboPlano.getSelectedItem();
                String recepcionista = (String) comboRecepcionista.getSelectedItem();
                if (!nome.isEmpty() && !cpf.isEmpty() && plano != null && recepcionista != null) {
                    Paciente paciente = new Paciente(cpf, nome, idade, plano, recepcionista);
                    cadastrarPaciente(paciente);
                } else {JOptionPane.showMessageDialog(null, "Preencha todos os campos!", null, JOptionPane.ERROR_MESSAGE);}

            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(null, "Idade inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        });

        painelFormulario.add(labelNome);
        painelFormulario.add(fieldNome);
        painelFormulario.add(labelCPF);
        painelFormulario.add(fieldCPF);
        painelFormulario.add(labelIdade);
        painelFormulario.add(fieldIdade);
        painelFormulario.add(labelPlano);
        painelFormulario.add(comboPlano);
        painelFormulario.add(labelRecepcionista);
        painelFormulario.add(comboRecepcionista);
        painelFormulario.add(botaoCadastrarPaciente);

        JOptionPane.showMessageDialog(this, painelFormulario, "Cadastrar Paciente", JOptionPane.PLAIN_MESSAGE);
    }


    private void mostrarFormularioConsulta() {
        carregarConsultas();
        JPanel painelConsulta = new JPanel(new GridLayout(7, 1, 20, 20));

        JLabel labelCPF = new JLabel("CPF: ");
        JTextField fieldCPF = new JTextField();

        JLabel labelEspecialidade = new JLabel("Especialidade: ");
        JComboBox<String> comboEspecialidade = new JComboBox<>(especialidadeMedicos.keySet().toArray(new String[0]));

        JLabel labelMedico = new JLabel("Médico: ");
        JComboBox<String> comboMedico = new JComboBox<>();

        comboEspecialidade.addActionListener(e -> {
            String especialidadeSelecionada = (String) comboEspecialidade.getSelectedItem();
            if (comboEspecialidade.getSelectedItem() != null) {
                comboMedico.removeAllItems();
            }
            for (Medico medico : especialidadeMedicos.get(especialidadeSelecionada)) {
                comboMedico.addItem(medico.getNome());
            }
        });

        JLabel labelRecepcionista = new JLabel("Recepcionista: ");
        ArrayList<String> rnome = new ArrayList<>();
        for (Recepcionista recepcionista : recepcionistas) {
            rnome.add(recepcionista.getNome());}
        JComboBox<String> comboRecepcionista = new JComboBox<>(rnome.toArray(new String[0]));


        // FEITO POR GPT
        JLabel labelData = new JLabel("Data da Consulta:");
        JSpinner spinnerData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerData, "dd/MM/yyyy");
        spinnerData.setEditor(dateEditor);
        JLabel labelHora = new JLabel("Hora da Consulta:");
        JSpinner spinnerHora = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerHora, "HH:mm");
        spinnerHora.setEditor(timeEditor);
        //

        JButton botaoAgendar = new JButton("Agendar Consulta");
        botaoAgendar.addActionListener(e -> {
            String cpf = fieldCPF.getText().trim();
            String especialidade = (String) comboEspecialidade.getSelectedItem();
            String medico = (String) comboMedico.getSelectedItem();
            String recepcionista = (String) comboRecepcionista.getSelectedItem();

            try {
                if (cpf.isEmpty() || especialidade == null || especialidade.isEmpty() ||
                        medico == null || medico.isEmpty() || recepcionista == null || recepcionista.isEmpty()) {
                    throw new CampoVazioException("Preencha todos os campos para continuar");
                }

            }
            catch (CampoVazioException v) {
                JOptionPane.showMessageDialog(null, v.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            }


            //GPT
            Date data = (Date) spinnerData.getValue();
            Date hora = (Date) spinnerHora.getValue();
            Calendar dataConsultaCalendar = Calendar.getInstance();
            dataConsultaCalendar.setTime(data);
            Calendar horaConsultaCalendar = Calendar.getInstance();
            horaConsultaCalendar.setTime(hora);
            dataConsultaCalendar.set(Calendar.HOUR_OF_DAY, horaConsultaCalendar.get(Calendar.HOUR_OF_DAY));
            dataConsultaCalendar.set(Calendar.MINUTE, horaConsultaCalendar.get(Calendar.MINUTE));
            dataConsultaCalendar.set(Calendar.SECOND, 0);
            Calendar agora = Calendar.getInstance();
            if (dataConsultaCalendar.before(agora)) {
                JOptionPane.showMessageDialog(null, "Data/Hora Inválidas", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //

            if (!pacienteExiste(cpf)) {
                JOptionPane.showMessageDialog(null, "Paciente não Existe", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //GPT
            SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm");
            String dataConsulta = dataFormat.format(data);
            String horaConsulta = horaFormat.format(hora);
            //
            Consulta consulta = new Consulta(cpf, especialidade, medico, recepcionista, dataConsulta, horaConsulta);
            salvarConsultas(consulta);
            JOptionPane.showMessageDialog(null, "Consulta marcada com sucesso!", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
        });

        painelConsulta.add(labelCPF);
        painelConsulta.add(fieldCPF);
        painelConsulta.add(labelEspecialidade);
        painelConsulta.add(comboEspecialidade);
        painelConsulta.add(labelMedico);
        painelConsulta.add(comboMedico);
        painelConsulta.add(labelRecepcionista);
        painelConsulta.add(comboRecepcionista);
        painelConsulta.add(labelData);
        painelConsulta.add(spinnerData);
        painelConsulta.add(labelHora);
        painelConsulta.add(spinnerHora);
        painelConsulta.add(botaoAgendar);

        JOptionPane.showMessageDialog(this, painelConsulta, "Marcar Consulta", JOptionPane.PLAIN_MESSAGE);
    }

    private boolean pacienteExiste(String cpf) {
        try {
            carregarPacientes();
        }
        catch (RuntimeException erro) {
            return false;
        }
        for (Paciente paciente : pacientes) {
            if (paciente.getCPF().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    private void cadastrarPaciente(Paciente paciente) {
        for (Paciente p : pacientes) {
            if (p.getCPF().equals(paciente.getCPF())) {
                JOptionPane.showMessageDialog(null, "Paciente já Cadastrado com Esse CPF", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        salvarCadastroPacientes(paciente);
        JOptionPane.showMessageDialog(null, "Paciente Cadastrado com Sucesso", null, JOptionPane.INFORMATION_MESSAGE);
    }

    public void salvarCadastroPacientes(Paciente paciente) {
        File arquivo = new File("cadastros_pacientes.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, true))) {
            bw.write(paciente.getCPF() + ";" +
                    paciente.getNome() + ";" +
                    paciente.getIdade() + ";" +
                    paciente.getPlano() + ";" +
                    paciente.getRecepcionistaCadastro() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void salvarConsultas(Consulta consulta) {
        File arquivo = new File("consultas_marcadas.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, true))) {
            bw.write(consulta.getCPF() + ";" +
                    consulta.getEspecialidade() + ";" +
                    consulta.getMedico() + ";" +
                    consulta.getRecepcionista() + ";" +
                    consulta.getDataConsulta() + ";" +
                    consulta.getHoraConsulta() + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar consulta", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Hospital hospital;
            try {
                hospital = new Hospital();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            hospital.setVisible(true);
        });
    }
}