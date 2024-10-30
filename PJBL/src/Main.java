class Hospital {
    ponto ponto barra barra
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

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public abstract void falar();
}

class Medico extends Pessoa {
    private String especialidade;
    private double salario;

    public Medico(String CPF, String nome, int idade, String especialidades) {
        super(CPF, nome, idade);
        this.especialidade = especialidade;
        this.salario = salario;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade {
        this.especialidade = especialidade
    }

    public double getSalario {
        return salario;
    }

    public void setSalario {
        this.salario = salario;
    }

    public void mostrarInformacoes() {
        System.out.println("Médico: " + nome + ", Idade: " + idade + ", Especialidade: " + especialidade + ", Salário: " + salario);
    }

    public String toString() {
        return "Médico " + nome + " (" + especialidade + ")" + "= Salário: " + salario;
    }
}

class Paciente extends Pessoa {
    String plano;
    public Paciente (String nome, String CPF, int idade, String plano){
        super (nome, CPF, idade);
        this.plano = plano;
    }
    public sentirDor (){
        System.out.println("AI AI!");
    }
}

class Recepcionista extends Pessoa {

}

public class Main {
    public static void main(String[] args) {
    }
}
