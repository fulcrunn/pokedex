from fastapi import FastAPI

app = FastAPI()

@app.get("/")
def read_root():
    return {"message": "API da Pokédex está rodando com Docker na VPS!"}

@app.get("/teste")
def teste():
    return {"status": "ok", "treinador": "Ash Ketchum"}
