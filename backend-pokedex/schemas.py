from pydantic import BaseModel
from typing import List, Optional

# --- Schemas para Login ---
class UsuarioLogin(BaseModel):
    usuario: str
    senha: str

# --- Schemas para Pokémon ---
class PokemonCreate(BaseModel):
    nome: str
    tipoPokemon: str  # No Android é Enum, aqui chega como String (Ex: "FOGO")
    habilidade: List[str] # Lista de strings
    usuario: str # Quem cadastrou

    class Config:
        orm_mode = True

class PokemonResponse(PokemonCreate):
    id: int # Na resposta, mandamos o ID também
