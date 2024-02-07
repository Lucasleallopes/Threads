package com.mycompany.cinema;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.ThreadLocalRandom;

public class Main_semThreads {
    public static void main(String[] args) {
        final Logger logger = LogManager.getLogger(Main_semThreads.class);
        long inicio = System.currentTimeMillis();
        
        logger.info("Iniciando o aplicativo...");
        System.out.println("EXECUÇÃO SEM THREADS");
        
        Cinema cinema = new Cinema("CineLucaos", "rua Dicaprio 702");
        System.out.println("\n__________Bem vindo(a) ao "+cinema.getNome()+"__________");
        
        Sessao sessao = new Sessao("Titanic", "Sala 1");
        cinema.adicionarSessao(sessao);

        List<Cliente> clientes = new ArrayList<>();

        // Criar e adicionar a quantidade de clientes
        for (int i = 1; i <= 20; i++) {
            String nome = gerarNomeAleatorio();
            String telefone = telefonesAleatorios();
            String email = "Cliente" + i + "@seuemail.com";
            clientes.add(new Cliente(nome, telefone, email));
        }

        List<Reserva> reservas = new ArrayList<>();
        logger.info("Iniciando as reservas...\n");
        
        Random random = new Random();
        for (Cliente cliente : clientes) {
            
            String codigoAssento = String.format("%c%d", (char) ('A' + random.nextInt(8)), random.nextInt(10) + 1);
            Reserva reserva = sessao.reservarAssento(cliente, codigoAssento);

            if (reserva != null) {
                reservas.add(reserva);
                System.out.println("|Reserva para " + cliente.getNome() + ": Assento " + reserva.getAssento().getCodigo());
            } else {
                System.out.println("|Assento ocupado e não disponível para " + cliente.getNome());
            }
        }

        System.out.println();
        logger.info("Reservas concluídas.");
        
        // Mostrando informações de todos os clientes e suas reservas
        System.out.println("\nInformacoes dos Clientes e suas Reservas:");
        for (Cliente cliente : clientes) {
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Telefone: "+cliente.getTelefone());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println("Reservas:");
            for (Reserva reserva : reservas) {
                if (reserva.getCliente() == cliente) {
                    System.out.println("  - Sessão: " + reserva.getSessao().getFilme());
                    System.out.println("  - Assento: " + reserva.getAssento().getCodigo());
                    System.out.println("  - Valor: " + reserva.getAssento().getPreco());
                }
            }
            System.out.println();
        }
        logger.info("Executando Assentos Disponiveis...\n ");
        
        // Mostrando assentos disponíveis
        System.out.println("Assentos Disponiveis:");
        List<Assento> assentosDisponiveis = sessao.listarAssentosDisponiveis();
        for (int i = 1; i <= 8; i++) {
            System.out.print("|" + (char) ('A' + i - 1) + ": ");
            for (int j = 1; j <= 9; j++) {
                String codigoAssento = String.format("%c%d", (char) ('A' + i - 1), j);
                if (assentosDisponiveis.stream().anyMatch(assento -> assento.getCodigo().equals(codigoAssento))) {
                    System.out.print(codigoAssento + " ");
                } else {
                    System.out.print("XX "); // 'X' para assento ocupado
                }
            }
            System.out.println();
        }
        System.out.println("             TELA \n");
        
        long fim = System.currentTimeMillis();
        long tempoDeExecucao = fim - inicio;
        logger.info("Tempo de Execucao: " + tempoDeExecucao + " milissegundos");
    }
    
    private static String telefonesAleatorios() {
        StringBuilder phoneNumber = new StringBuilder("+55 (");
        phoneNumber.append(ThreadLocalRandom.current().nextInt(41, 45)); // DDD do Paraná
        phoneNumber.append(") 9");
        for (int i = 0; i < 8; i++) {
            phoneNumber.append(ThreadLocalRandom.current().nextInt(0, 10));
            if (i == 3) {
                phoneNumber.append("-"); // Adiciona um hífen após o código do país, DDD e 4º dígito
            }
        }
        return phoneNumber.toString();
    }

    private static String gerarNomeAleatorio() {
        String[] nomes = {
            "Lucas", "Ana", "Pedro", "Maria", "João", "Carla", "Rafael", "Julia", "Fernando", "Amanda",
            "Elenilton", "Mariana", "Gustavo", "Camila", "Ricardo", "Isabela", "Diego", "Patrícia", "André", "Natália",
            "Thiago", "Larissa", "Eduardo", "Bianca", "Felipe", "Leticia", "Vinicius", "Monique", "Daniel", "Vanessa",
            "Roberto", "Tatiana", "Leonardo", "Fernanda", "Gabriel", "Renata", "Antônio", "Marta", "Carlos", "Sara",
            "Manuel", "Laura", "José", "Isadora", "Marcos", "Elisa", "Luiz", "Cristina", "Rodrigo", "Alice"
        };
        String[] sobrenomes = {
            "Silva", "Santos", "Oliveira", "Lopes", "Ferreira", "Ribeiro", "Costa", "Rodrigo", "Machado", "Gonçalves",
            "Almeida", "Pereira", "Nascimento", "Carvalho", "Araújo", "Rocha", "Martins", "Freitas", "Correia", "Neves",
            "Melo", "Barbosa", "Vieira", "Tavares", "Dias", "Campos", "Cardoso", "Fernandes", "Borges", "Monteiro",
            "Cavalcanti", "Dantas", "Gomes", "Sousa", "Lima", "Pinto", "Ramos", "Castro", "Pereira", "Fonseca", "Reis",
            "Azevedo", "Leal", "Mendes", "Siqueira", "Cunha", "Alves", "Dantas", "Rocha", "Farias"
        };

        Random random = new Random();
        String nome = nomes[random.nextInt(nomes.length)];
        String sobrenome = sobrenomes[random.nextInt(sobrenomes.length)];

        return nome + " " + sobrenome;
    }
}
