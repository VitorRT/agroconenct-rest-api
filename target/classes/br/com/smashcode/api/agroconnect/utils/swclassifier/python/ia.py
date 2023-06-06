import joblib
import os
import nltk
nltk.download('stopwords')
from nltk import tokenize
import unidecode
from string import punctuation
from sklearn.feature_extraction.text import CountVectorizer
import scipy.sparse as sp

path_input = "./src/main/java/br/com/smashcode/api/agroconnect/utils/swclassifier/python/input.txt"
path_model = "./src/main/java/br/com/smashcode/api/agroconnect/utils/swclassifier/python/modelo_ml.pkl"



def ler_arquivo(path):
    arquivo = open(path_input, 'r')
    in_arq = arquivo.read()
    arquivo.close()
    return in_arq

def excluir_arquivo(path):
    try:    
        os.remove(path)
    except FileNotFoundError:
        print("O arquivo não foi encontrado.")
    except PermissionError:
        print("Sem permissão para excluir o arquivo.")
    except OSError:
        print("Sem permissão para excluir o arquivo.")

def get_input(path):
    inp = ler_arquivo(path)
    if inp == None:
        print("1")
        return
    excluir_arquivo(path)
    return inp
    

input_ia = get_input(path_input)

model = joblib.load(path_model)

pontuacao = list()
for ponto in punctuation:
    pontuacao.append(ponto)

palavras_irrelevantes = nltk.corpus.stopwords.words("portuguese")
pontuacao_stopwords = pontuacao + palavras_irrelevantes
stopwords_sem_acento =  [unidecode.unidecode(texto) for texto in pontuacao_stopwords]

vetorize2 = CountVectorizer(lowercase = False)

def pre_processamento(texto):
    lista_frase1 = list()
    lista_frase2 = list()
    tokenizer2 = tokenize.WordPunctTokenizer()
    p1 = texto.lower()
    p2 = tokenizer2.tokenize(p1)
    for i in p2:
        if i not in stopwords_sem_acento:
            lista_frase1.append(i)
    lista_frase2.append(' '.join(lista_frase1))
    return lista_frase2

frase = pre_processamento(input_ia)
frase_v = vetorize2.fit_transform(frase)

num_linhas, num_colunas = frase_v.shape
resultado_final = sp.lil_matrix((num_linhas, 118))
resultado_final[:, :num_colunas] = frase_v
resultado_final = resultado_final.tocsr()

teste = model.predict(resultado_final)

if teste[0] == 1:
    # tem xingamentos
    print("0")
else:
    # não tem xingamentos
    print("1")
