/* ModeloFisicoAtelie: */
CREATE DATABASE Atelie;
USE Atelie;


CREATE TABLE Cliente (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(50),
    telefone VARCHAR(50),
    email VARCHAR(50)
   
);

CREATE TABLE Medida (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(50),
    tamanho DOUBLE
);

CREATE TABLE Modelo (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(50),    
    multiplicador DOUBLE
);

CREATE TABLE Peca (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(50),
    precoBase DOUBLE,
    medidas INTEGER
);

CREATE TABLE Cliente_Medida (
    idCliente INTEGER,
    idMedida INTEGER,
    FOREIGN KEY (idCliente) REFERENCES Cliente (id),
    FOREIGN KEY (idMedida) REFERENCES Medida (id)
);

CREATE TABLE Medida_Peca (
    idMedida INTEGER,
    idPeca INTEGER
);

CREATE TABLE Peca_Modelo (
    idPeca INTEGER,
    idModelo INTEGER,
    FOREIGN KEY (idPeca) REFERENCES Peca (id)
);

CREATE TABLE Adicional (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(50),
    multiplicador DOUBLE
);

CREATE TABLE Tecido (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(50),
    preco DOUBLE
);

CREATE TABLE Usuario (
    id INTEGER PRIMARY KEY,
    nomeUsuario VARCHAR(50),
    senhaUsuario VARCHAR(20),
    emailUsuario VARCHAR(50)
);

CREATE TABLE Orcamento (
    id INTEGER PRIMARY KEY,
    id_usuario INTEGER,
    id_cliente INTEGER,
    dataCriacao DATE,
    valorTotal DOUBLE,
    observacoes VARCHAR(100),
    FOREIGN KEY (id_usuario) REFERENCES Usuario (id),
    FOREIGN KEY (id_cliente) REFERENCES Cliente (id)    
);

CREATE TABLE itemPedido (
    id INTEGER PRIMARY KEY,
    id_peca INTEGER,
    id_tamanho INTEGER,
    id_modelo INTEGER,
    id_tecido INTEGER,
    cor VARCHAR(50),
    id_adicional INTEGER,
    valorItem DOUBLE,
    FOREIGN KEY (id_modelo) REFERENCES Modelo (id),
    FOREIGN KEY (id_tecido) REFERENCES Tecido (id)
);

CREATE TABLE Orcamento_ItemPedido (
    id_orcamento INTEGER,
    id_itempedido INTEGER,
    FOREIGN KEY (id_orcamento) REFERENCES Orcamento (id),
    FOREIGN KEY (id_itempedido) REFERENCES itemPedido (id)
);

CREATE TABLE Pedido (
    id INTEGER PRIMARY KEY,
    dataEntrega DATE,
    dataPagamento DATE,
    id_orcamento INTEGER,
    tipoPagamento VARCHAR(50),
    situacao VARCHAR(50),
    FOREIGN KEY (id_orcamento) REFERENCES Orcamento (id)
);