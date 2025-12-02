from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
import os

# Pega a URL de conexão que definimos no docker-compose.yml
# Se não achar, usa um valor padrão (fallback)
DATABASE_URL = os.getenv("DATABASE_URL", "postgresql://usuario_poke:senha_secreta_poke@db:5432/pokedex_db")

engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base = declarative_base()

# Função para pegar uma sessão do banco em cada requisição
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()
