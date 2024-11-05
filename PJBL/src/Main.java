import java.util.ArrayList;

class Hospital {
//    private ArrayList<Pessoa.Medico> medicos = new ArrayList<>();
//    private ArrayList<Pessoa.Paciente> pacientes = new ArrayList<>();
//    private ArrayList<Pessoa.Recepcionista> recepcionistas = new ArrayList<>();
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

        public void verificarPlano() throws SemPlano {
            if (plano == null || plano.trim().isEmpty()){ // Verifica se o campo "plano" está vazio ou se está preenchido apenas com espaços
                throw new SemPlano("Plano indisponível para o usuário " + nome);
            }
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
    class SemPlano extends Exception {
        public SemPlano(String message) {
            super (message);
        }
    }


public class Main {
    public static void main(String[] args) {
        Paciente teste1 = new Paciente ("12345678910", "Mascos", 55, "Ouro");
        Paciente teste2 = new Paciente ("12465478990", "Johnatan da nova geração", 22, " ");

        try {
            teste1.verificarPlano();
            System.out.printf("%S tem um plano %S\n", teste1.getNome(), teste1.getPlano());
        } catch (SemPlano e){
            System.out.println(e.getMessage());
        }
        try {
            teste2.verificarPlano();
            System.out.printf("%S tem um plano %S\n", teste2.getNome(), teste2.getPlano());
        } catch (SemPlano e){
            System.out.println(e.getMessage());
        }
    }
}