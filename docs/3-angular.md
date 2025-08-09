# 3. Angular

Minha experiência com Angular foca na criação de aplicações web dinâmicas (SPAs). Utilizei seus principais recursos como injeção de dependência para criar serviços reutilizáveis, HttpClient para consumir APIs REST e data binding para sincronizar os dados com a tela.
Um caso de uso prático foi exibir dados de produtos em uma tabela. Criei um serviço (ProdutoService) para buscar os dados da API. No componente, injetei este serviço e, no ngOnInit, chamei o método que retorna um Observable. Com o subscribe, alimentei uma propriedade do componente, que foi usada no template com a diretiva *ngFor para renderizar as linhas da tabela dinamicamente.

```typescript
// --- produto.component.ts ---
import { Component, OnInit } from '@angular/core';
import { ProdutoService } from '../produto.service';

@Component({
  selector: 'app-tabela-produtos',
  templateUrl: './produto.component.html'
})
export class ProdutoComponent implements OnInit {
    produtos: any[] = []; // Propriedade para vincular aos dados

    constructor(private produtoService: ProdutoService) {} // Injeção de dependência

    ngOnInit() {
      // Consome o serviço e atualiza a propriedade com os dados da API
      this.produtoService.getProdutos().subscribe(data => {
        this.produtos = data;
      });
    }
}

// --- produto.component.html ---
// <table>
//   <thead> <tr> <th>ID</th> <th>Nome</th> </tr> </thead>
//   <tbody>
//     //     <tr *ngFor="let produto of produtos">
//       <td>{{ produto.id }}</td>
//       <td>{{ produto.nome }}</td>
//     </tr>
//   </tbody>
// </table>

