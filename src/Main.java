class Hospital {

}

abstract class Pessoa {

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
