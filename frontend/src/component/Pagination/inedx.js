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
				<button
					key={index}
					disabled={isActive(index)}
					onClick={() => {
					}}
				>
					{index + 1}
				</button>
			))}
		</div>
	);
};
