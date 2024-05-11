import React from 'react';
import { Container, Button } from 'reactstrap';
import { CLIENT_TEAM_NAME } from '@utils/constants';
import Menu from './Menu';
import { useToggle } from '@hooks/useToggle';
import AddPlace from './AddPlace';
import LoadFile from './LoadFile'
import { IoMdClose } from 'react-icons/io';
import SaveFile from './SaveFile';

export default function Header(props) {
	const [showAddPlace, toggleAddPlace] = useToggle(false);
	const [showLoadFile, toggleLoadFile] = useToggle(false);
	const [showSaveFile, toggleSaveFile] = useToggle(false);

	const toggles = {
		toggleAddPlace, toggleLoadFile, toggleAbout: props.toggleAbout, toggleSaveFile
	}

	const shows = {
		showAddPlace, showLoadFile, showAbout: props.showAbout, showSaveFile
	}
	
	return (
		<React.Fragment>
			<HeaderContents
				{...toggles}
				{...props}
			/>
			<AppModals
				{...shows}
				{...toggles}
				{...props}
			/>
		</React.Fragment>
	);
}

function HeaderContents(props) {
	return (
		<div className='full-width header vertical-center'>
			<Container>
				<div className='header-container'>
					<h1
						className='tco-text-upper header-title'
						data-testid='header-title'
					>
						{CLIENT_TEAM_NAME}
					</h1>
					<HeaderButton {...props} />
				</div>
			</Container>
		</div>
	);
}

function HeaderButton(props) {
	return props.showAbout ? (
		<Button
			data-testid='close-about-button'
			color='primary'
			onClick={() => props.toggleAbout()}
		>
			<IoMdClose size={32} />
		</Button>
	) : (
		<Menu {...props}/>
	);
}

function AppModals(props) {
	return (
		<>
			<AddPlace
				{...props}
			/>
			<LoadFile
				{...props}
			/>
			<SaveFile
				{...props}
			/>
		</>
	);
}
