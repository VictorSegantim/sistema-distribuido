package algunsprojetos;

import java.util.Random;

public class Pessoa {
		
	
		String NomeAluno, Registro, Disciplina;
		float primeiraNota, segundaNota, terceiraNota;
	
		public void setAluno(String NomeReg, String Reg, float NotaDa1, float NotaDa2, float NotaDa3, String CursoReg) {
			NomeAluno = NomeReg;
			Registro = Reg;
			primeiraNota = NotaDa1;
			segundaNota = NotaDa2;
			terceiraNota = NotaDa3;
			Disciplina = CursoReg;
		}
		
		public String getAluno() {
			System.out.println("--------------------------------\n");
			String retornoMensagem = "O registro do aluno � " + Registro + ", Nota 1 � " + primeiraNota + ", Nota 2 � " + segundaNota + ", Nota 3 � " + terceiraNota + ", Disciplina � " + Disciplina;
			
			return retornoMensagem;
		}
		
		
		public void mostraAluno() {
			System.out.println(getAluno());
		}
		
		public Long boletoAleatorio() {
			Random aleatorio = new Random();
			Long valor = aleatorio.nextLong();
			
			return (valor);
		}
		
		public float mediaNotas() {
			float media = (primeiraNota + segundaNota + terceiraNota) / 3;
			
			return media;
		}
	
		
	
}
