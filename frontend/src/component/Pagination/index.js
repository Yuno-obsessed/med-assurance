export const Pagination = ({
	                           totalPages,
	                           onHandlePage,
	                           activePage,
                           }) => {
	const isActive = (index) => {
		return activePage === index;
	};

	return (
		<div>
			{Array.from({ length: totalPages }).map((_, index) => (
				<button style={{width: 50}}
					key={index}
					disabled={isActive(index)}
					onClick={() => {
						onHandlePage(index)
					}}
				>
					{index + 1}
				</button>
			))}
		</div>
	);
};
