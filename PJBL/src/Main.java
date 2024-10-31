import java.util.ArrayList;

class Hospital {
    private ArrayList<Pessoa.Medico> medicos = new ArrayList<>();
    private ArrayList<Pessoa.Paciente> pacientes = new ArrayList<>();
    private ArrayList<Pessoa.Recepcionista> recepcionistas = new ArrayList<>();
    String nome;

    public Hospital() {

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
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }


    class Medico extends Pessoa {
        private String especialidade;
        private String CRM;

        public Medico(String CPF, String nome, int idade, String especialidade, double salario, String CRM) {
            super(CPF, nome, idade);
            this.especialidade = especialidade;
            this.CRM = CRM;
        }

        public String getEspecialidade() {
            return especialidade;
        }

        public void setEspecialidade(String especialidade) {
            this.especialidade = especialidade;
        }

        public String getCRM() {
            return CRM;
        }

        public void setCRM(String CRM) {
            this.CRM = CRM;
        }

        public void mostrarInformacoes() {
            System.out.println("Médico: " + nome + ", Idade: " + idade + ", Especialidade: " + especialidade);
        }

        @Override
        public String toString() {
            return "Médico " + nome + " (" + especialidade + ") - Salário: ";
        }
    }


    class Paciente extends Pessoa {
        private String plano;

        public Paciente(String CPF, String nome, int idade, String plano) {
            super(CPF, nome, idade);
            this.plano = plano;
        }

        public String getPlano() {
            return plano;
        }

        public void setPlano(String plano) {
            this.plano = plano;
        }

        public void sentirDor() {
            System.out.println("AI AI!");
        }
    }


    class Recepcionista extends Pessoa {
        private String turno;

        public Recepcionista(String nome, String CPF, int idade, String turno) {
            super(nome, CPF, idade);
            this.turno = turno;
        }

        public void agendarConsulta() {
            System.out.printf("%S: Agendou uma consulta para o paciente\n", nome);
        }

        public void exibirInformacao() {
            System.out.printf("Nome: %S\nTurno: %S\n", nome, turno);
        }
    }
}

public class Main {
    public static void main(String[] args) {
    }
}