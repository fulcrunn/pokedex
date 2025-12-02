from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session
from typing import List
import models, schemas, database

# 1. Cria as tabelas no banco automaticamente
models.Base.metadata.create_all(bind=database.engine)

app = FastAPI()

# --- ROTA DE TESTE ---
@app.get("/teste")
def teste():
    return {"status": "ok", "treinador": "Ash Ketchum"}

# --- 2. REGISTRO AUTOMÁTICO DE USUÁRIOS ---
@app.on_event("startup")
def startup_users():
    db = database.SessionLocal()
    # Lista de usuários para criar automaticamente
    usuarios_padrao = [
        ("admin", "1234"),
        ("ash", "pikachu"),
        ("misty", "togepi")
    ]
    for user, pwd in usuarios_padrao:
        # Verifica se já existe para não duplicar
        existe = db.query(models.User).filter(models.User.username == user).first()
        if not existe:
            novo = models.User(username=user, password=pwd)
            db.add(novo)
            print(f"Criando usuario padrao: {user}")
    db.commit()
    db.close()

# --- 3. ROTA DE LOGIN ---
@app.post("/login")
def login(dados: schemas.UsuarioLogin, db: Session = Depends(database.get_db)):
    user = db.query(models.User).filter(models.User.username == dados.usuario).first()
    
    if not user:
        raise HTTPException(status_code=400, detail="Usuário não encontrado")
    
    if user.password != dados.senha:
        raise HTTPException(status_code=400, detail="Senha incorreta")
    
    return {"mensagem": "Login ok", "usuario": user.username}

# --- 4. ROTA DE CADASTRAR POKÉMON ---
@app.post("/pokemons")
def criar_pokemon(poke: schemas.PokemonCreate, db: Session = Depends(database.get_db)):
    existe = db.query(models.PokemonDB).filter(models.PokemonDB.nome == poke.nome).first()
    if existe:
        raise HTTPException(status_code=400, detail="Pokémon já existe!")

    habs_str = ",".join(poke.habilidade) # Converte lista pra string

    novo_poke = models.PokemonDB(
        nome=poke.nome,
        tipo=poke.tipoPokemon,
        habilidades=habs_str,
        dono=poke.usuario
    )
    
    db.add(novo_poke)
    db.commit()
    return {"mensagem": "Sucesso", "id": novo_poke.id}

# --- 5. ROTA DE LISTAR POKÉMONS ---
@app.get("/pokemons", response_model=List[schemas.PokemonResponse])
def listar_pokemons(db: Session = Depends(database.get_db)):
    pokemons_db = db.query(models.PokemonDB).all()
    
    lista_retorno = []
    for p in pokemons_db:
        lista_habs = p.habilidades.split(",") if p.habilidades else []
        obj = schemas.PokemonResponse(
            id=p.id,
            nome=p.nome,
            tipoPokemon=p.tipo,
            habilidade=lista_habs,
            usuario=p.dono
        )
        lista_retorno.append(obj)
        
    return lista_retorno
