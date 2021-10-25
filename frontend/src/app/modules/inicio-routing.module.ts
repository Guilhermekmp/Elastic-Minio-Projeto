import { AnexoModule } from './anexo/anexo.module';
import { TarefaModule } from './tarefa/tarefa.module';
import { ResponsavelModule } from './responsavel/responsavel.module';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
    {
        path: 'responsavel',
        loadChildren:() => ResponsavelModule
    },
    {
        path: 'tarefa',
        loadChildren:() => TarefaModule
    },
    {
        path: 'anexo',
        loadChildren:() => AnexoModule
    },
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })
export class InicioRoutingModule {}
