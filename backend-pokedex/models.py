from sqlalchemy import Column, Integer, String
from database import Base

class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)
    username = Column(String, unique=True, index=True)
    password = Column(String)

class PokemonDB(Base):
    __tablename__ = "pokemons"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String, unique=True, index=True) # Nome único (Requisito do trabalho)
    tipo = Column(String)
    habilidades = Column(String) # Vamos salvar como texto separado por vírgula (Ex: "FOGO,VOADOR")
    dono = Column(String) # Nome do usuário que cadastrou
