'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {clientes: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/clientes'}).done(response => {
			this.setState({clientes: response.entity});
		});
	}


	onCreate(newCliente) {
    	client({
    		method: 'POST',
    		path: '/clientes',
    		entity: newCliente,
    		headers: {'Content-Type': 'application/json'}
    	})
    }

	render() {
		return (
		    <div>
			    <CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
			    <ClienteList clientes={this.state.clientes}/>
			</div>
		)
	}
}

class ClienteList extends React.Component{
	render() {
	    const clientes = this.props.clientes.map(cliente =>
        	<Cliente key={cliente.id} cliente={cliente}/>
        );
        return (
            <table>
        	    <tbody>
        		    <tr>
        			    <th>Id</th>
        				<th>Limite</th>
        				<th>Saldo</th>
        			</tr>
        			{clientes}
        		</tbody>
        	</table>
        )
	}
}

class Cliente extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.cliente.id}</td>
				<td>{this.props.cliente.limite}</td>
				<td>{this.props.cliente.saldo}</td>
				<td>
                	<Extrato cliente={this.props.cliente}/>
                </td>
			</tr>
		)
	}
}

class Extrato extends React.Component {

	constructor(props) {
		super(props);
		this.state = {clienteExtrato: {"saldo":{"total":0,"data_extrato":"","limite": 0},"ultimas_transacoes":[]}};
	}

	loadExtrato(dialogId) {
	    //e.preventDefault();
      	client({method: 'GET', path: '/clientes/' + this.props.cliente.id + "/extrato"}).done(response => {
      	    this.setState({clienteExtrato: response.entity});
        });
        window.location = "#" + dialogId;
    }

	render() {
	    const dialogId = "extratoDialog" + this.props.cliente.id;
	    const ultimasTransacoes = this.state.clienteExtrato.ultimas_transacoes.map(trs =>
              <Transacao transacao={trs}/>
        );
		return (
		    <div>
			    <a href={"#" + dialogId} onClick={() => this.loadExtrato(dialogId)}>Extrato</a>
  				<div id={dialogId} className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Extrato</h2>

                        <h3>Saldo</h3>
                        <div>
                            <p key="total"><b>Total:</b> {this.state.clienteExtrato.saldo.total}</p>
                            <p key="limite"><b>Limite:</b> {this.state.clienteExtrato.saldo.limite}</p>
                        </div>

                        <h3>Transações</h3>
                        <div>
                                 <table>
                                       <tbody>
                                            <tr>
                                        	    <th>Valor</th>
                                        		<th>Tipo</th>
                                        		<th>Realizada em</th>
                                        		<th>Descricao</th>
                                        	</tr>
                                        	{ultimasTransacoes}
                                        </tbody>
                                 </table>
                        </div>
					</div>
				</div>
			</div>
		);
	}

}

class Transacao extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.transacao.valor}</td>
				<td>{this.props.transacao.tipo}</td>
				<td>{this.props.transacao.realizada_em}</td>
				<td>{this.props.transacao.descricao}</td>
			</tr>
		)
	}
}


class CreateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		const newCliente = {};
       	newCliente['id'] = ReactDOM.findDOMNode(this.refs['limite']).value.trim();
       	newCliente['limite'] = ReactDOM.findDOMNode(this.refs['limite']).value.trim();
       	newCliente['saldo'] = ReactDOM.findDOMNode(this.refs['saldo']).value.trim();
		this.props.onCreate(newCliente);
		window.location = "#";
	}

	render() {
		return (
			<div>
				<a href="#createCliente">Create</a>

				<div id="createCliente" className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Create new cliente</h2>

						<form>
						    <p key="id">
                                <input type="text" placeholder="id" ref="id" className="field"/>
                            </p>
							<p key="limite">
                            	<input type="text" placeholder="limite" ref="limite" className="field"/>
                            </p>
							<p key="saldo">
                            	<input type="text" placeholder="saldo" ref="saldo" className="field"/>
                            </p>
							<button onClick={this.handleSubmit}>Create</button>
						</form>
					</div>
				</div>
			</div>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)