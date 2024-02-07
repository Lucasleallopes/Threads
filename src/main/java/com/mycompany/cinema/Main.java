package com.mycompany.cinema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final List<Reserva> reservas = Collections.synchronizedList(new ArrayList<>()); // Lista de reservas

    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();

        logger.info("Iniciando o aplicativo...");
       
        Cinema cinema = new Cinema("CineLucaos", "rua Dicaprio 702");
        System.out.println("\n__________Bem vindo(a) ao " + cinema.getNome() + "__________");

        Sessao sessao = new Sessao("Titanic", "Sala 1");
        cinema.adicionarSessao(sessao);
        List<Cliente> clientes = gerarClientes(20); // Criar clientes aqui

        logger.info("Iniciando as Threads...\n");

        ExecutorService executorService = Executors.newCachedThreadPool();
        clientes.forEach(cliente -> executorService.submit(() -> processarReserva(sessao, cliente, reservas)));

        desligarExecutor(executorService);
        
        mostrarInformacoesClientes(clientes, reservas);

        logger.info("Executando Assentos Disponíveis...\n");
        
        mostrarAssentosDisponiveis(sessao);

        long fim = System.currentTimeMillis();
        logger.info("Tempo de Execucao: " + (fim - inicio) + " milissegundos");
    }

    private static void processarReserva(Sessao sessao, Cliente cliente, List<Reserva> reservas) {
        String codigoAssento = gerarCodigoAssentoAleatorio();
        Reserva reserva = sessao.reservarAssento(cliente, codigoAssento);
        if (reserva != null) {
            synchronized (reservas) {
                reservas.add(reserva);
            }
            System.out.println("|Reserva para " + cliente.getNome() + ": Assento " + reserva.getAssento().getCodigo());
        } else {
            System.out.println("|Assento ocupado e não disponível para " + cliente.getNome());
        }
    }

    private static void desligarExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    private static List<Cliente> gerarClientes(int quantidade) {
        List<Cliente> clientes = new ArrayList<>();
        for (int i = 1; i <= quantidade; i++) {
            String nome = gerarNomeAleatorio();
            String telefone = telefonesAleatorios();
            String email = "Cliente" + i + "@seuemail.com";
            clientes.add(new Cliente(nome, telefone, email));
        }
        return clientes;
    }

    private static void mostrarInformacoesClientes(List<Cliente> clientes, List<Reserva> reservas) {
        System.out.println("\nInformações dos Clientes e suas Reservas:");
        clientes.forEach(cliente -> {
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Telefone: " + cliente.getTelefone());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println("Reservas:");
            reservas.stream()
                    .filter(reserva -> reserva.getCliente().equals(cliente))
                    .forEach(reserva -> {
                        System.out.println("  - Sessão: " + reserva.getSessao().getFilme());
                        System.out.println("  - Assento: " + reserva.getAssento().getCodigo());
                        System.out.println("  - Valor: " + reserva.getAssento().getPreco());
                    });
            System.out.println();
        });
    }

    private static void mostrarAssentosDisponiveis(Sessao sessao) {
        System.out.println("Assentos Disponíveis:");
        List<Assento> assentosDisponiveis = sessao.listarAssentosDisponiveis(); // Supondo que esta função retorna uma lista de assentos disponíveis
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
    }

    private static String gerarCodigoAssentoAleatorio() {
        return String.format("%c%d", (char) ('A' + ThreadLocalRandom.current().nextInt(8)), ThreadLocalRandom.current().nextInt(10) + 1);
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
