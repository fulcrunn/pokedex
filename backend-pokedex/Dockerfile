FROM python:3.9-slim

WORKDIR /app

# Instala dependências do sistema para compilar o driver do Postgres
RUN apt-get update \
    && apt-get -y install libpq-dev gcc \
    && pip install psycopg2-binary

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]
