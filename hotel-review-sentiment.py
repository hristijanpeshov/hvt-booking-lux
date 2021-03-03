import numpy as np
from flask import Flask, jsonify
import torch
from torch.utils.data import DataLoader, RandomSampler, SequentialSampler
import torch.nn.functional as F
from transformers import BertTokenizer
from torch.utils.data import TensorDataset
from transformers import BertTokenizer
from torch.utils.data import TensorDataset
from tqdm.notebook import tqdm
from flask import request

app = Flask(__name__)
model = torch.load("./hotel_dir/hotelModel",
                   map_location=torch.device('cpu'))


def init(review):
    tokenizer = BertTokenizer.from_pretrained(
        'bert-base-uncased',
        do_lower_case=True
    )

    encoded_data = tokenizer.batch_encode_plus(
        [review],
        add_special_tokens=True,
        return_attention_mask=True,
        pad_to_max_length=True,
        max_length=256,
        return_tensors='pt'
    )
    input_ids = encoded_data['input_ids']
    attention_masks = encoded_data['attention_mask']
    dataset = TensorDataset(input_ids,
                            attention_masks)

    return DataLoader(
        dataset,
        sampler=RandomSampler(dataset),
        batch_size=32
    )


def bert_predict(model, test_dataloader):
    """Perform a forward pass on the trained BERT model to predict probabilities
    on the test set.
    """
    # Put the model into the evaluation mode. The dropout layers are disabled during
    # the test time.
    model.eval()
    device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
    all_logits = []

    # For each batch in our test set...
    for batch in test_dataloader:
        # Load batch to GPU
        b_input_ids, b_attn_mask = tuple(t.to(device) for t in batch)[:2]

        # Compute logits
        with torch.no_grad():
            logits = model(b_input_ids, b_attn_mask)
        all_logits.append(logits)

    # Apply softmax to calculate probabilities
    probs = F.softmax(all_logits[0][0], dim=1).cpu().numpy()

    return probs


@app.route('/review', methods=['GET'])
def samplefunction():
    # request.json to read from json as input. It should be POST
    dataloader = init('The room was dirty and there was bad smell. We dont like the view. In one word awful.')
    prediction = bert_predict(model, dataloader)
    decision = prediction[0][0] > prediction[0][1]
    data = {"sentiment": str(decision)}
    return jsonify(data)


if __name__ == '__main__':
    port = 8000  # the custom port you want
    app.run(host='0.0.0.0', port=port)
