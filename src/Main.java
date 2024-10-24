class Hospital {

}

abstract class Pessoa {
    protected String CPF;
    protected String nome;
    protected int idade;

    public Pessoa(int CPF, String nome, int idade) {
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

}

class Paciente extends Pessoa {

}

class Recepcionista extends Pessoa {
    private int salario;

    public Recepcionista (String nome, String CPF, int idade, int salario) {
        super(nome, CPF, idade);
        this.salario = 2200;
    }
    public void agendarConsulta(){
        System.out.println("Agendou uma consulta para o paciente");
    }
}

public class Main {
    public static void main(String[] args) {
    }
}
